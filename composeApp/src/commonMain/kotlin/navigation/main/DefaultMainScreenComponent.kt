package navigation.main

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.pushNew
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.main.feed_details.DefaultFeedDetailsScreenComponent
import navigation.main.tabs.DefaultMainBottomTabsScreenComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultMainScreenComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
    private val onLogoutCallback: () -> Unit
) : MainScreenComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = {
            getInitialStack(
                webHistoryPaths = webHistoryController?.historyPaths, deepLink = deepLink
            )
        },
        handleBackButton = true,
        childFactory = ::child,
    )

    override val childStack: Value<ChildStack<*, MainScreenComponent.Config>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private fun child(config: Config, componentContext: ComponentContext): MainScreenComponent.Config = when (config) {
        is Config.MainTabs -> MainScreenComponent.Config.Tabs(
            component = DefaultMainBottomTabsScreenComponent(
                componentContext = componentContext,
                delegate = object : DefaultMainBottomTabsScreenComponent.Delegate {
                    override fun onLogout() {
                        onLogoutCallback()
                    }

                    override fun onFeedDetails(id: Long) {
                        navigation.pushNew(Config.FeedDetails(id = id))
                    }

                })
        )

        is Config.FeedDetails -> MainScreenComponent.Config.FeedDetails(
            component = DefaultFeedDetailsScreenComponent(
                componentContext = componentContext,
                onBackCallback = { navigation.pop() })
        )

    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object MainTabs : Config

        @Serializable
        data class FeedDetails(val id: Long) : Config
    }

    private companion object {
        private const val WEB_PATH_MAIN_TABS = ""
        private const val WEB_PATH_AUTH = "auth"
        private const val WEB_PATH_FEED_DETAILS = "feed"

        private fun getInitialStack(
            webHistoryPaths: List<String>?, deepLink: DeepLink
        ): List<Config> =
            webHistoryPaths?.takeUnless(List<*>::isEmpty)?.map(::getConfigForPath) ?: getInitialStack(deepLink)

        private fun getInitialStack(deepLink: DeepLink): List<Config> = when (deepLink) {
            is DeepLink.None -> listOf(Config.MainTabs)
            is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
        }

        private fun getPathForConfig(config: Config): String = when (config) {
            Config.MainTabs -> ""
            is Config.FeedDetails -> "/$WEB_PATH_FEED_DETAILS/${config.id}"
        }

        private fun getConfigForPath(path: String): Config = when (path.removePrefix("/")) {
            WEB_PATH_MAIN_TABS -> Config.MainTabs
            WEB_PATH_FEED_DETAILS -> Config.FeedDetails(id = path.filter { it.isDigit() }.toLong())
            else -> Config.MainTabs
        }
    }

    sealed interface DeepLink {
        data object None : DeepLink
        class Web(val path: String) : DeepLink
    }

}
