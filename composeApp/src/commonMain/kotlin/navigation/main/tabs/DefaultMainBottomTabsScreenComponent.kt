package navigation.main.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.main.feed.DefaultFeedScreenComponent
import navigation.main.home.DefaultHomeScreenComponent
import navigation.main.settings.DefaultSettingsScreenRootComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultMainBottomTabsScreenComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
    private val delegate: Delegate
) : MainBottomTabsScreenComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialStack = {
            getInitialStack(
                webHistoryPaths = webHistoryController?.historyPaths, deepLink = deepLink
            )
        },
        childFactory = ::child,
    )

    override val childStack: Value<ChildStack<*, MainBottomTabsScreenComponent.Tab>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private fun onLogout() {
        delegate.onLogout()
    }

    override fun onHomeTabClicked() {
        navigation.bringToFront(Config.Home)
    }

    override fun onFeedTabClicked() {
        navigation.bringToFront(Config.Feed)
    }

    override fun onFeedDetails(id: Long) {
        delegate.onFeedDetails(id = id)
    }

    override fun onSettingsTabClicked() {
        navigation.bringToFront(Config.Settings)
    }

    private fun child(config: Config, componentContext: ComponentContext): MainBottomTabsScreenComponent.Tab =
        when (config) {
            is Config.Home -> MainBottomTabsScreenComponent.Tab.Home(
                component = DefaultHomeScreenComponent(
                    componentContext = componentContext,
                    onLogoutCallback = { onLogout() })
            )

            is Config.Feed -> MainBottomTabsScreenComponent.Tab.Feed(component = DefaultFeedScreenComponent(componentContext = componentContext,
                delegate = object : DefaultFeedScreenComponent.Delegate {
                    override fun onLogout() {
                        onLogout()
                    }

                    override fun onDetails(id: Long) {
                        delegate.onFeedDetails(id = id)
                    }

                }))

            is Config.Settings -> MainBottomTabsScreenComponent.Tab.Settings(
                component = DefaultSettingsScreenRootComponent(
                    componentContext = componentContext,
                    onLogoutCallback = { onLogout() })
            )
        }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Home : Config

        @Serializable
        data object Feed : Config

        @Serializable
        data object Settings : Config
    }

    private companion object {
        private const val WEB_PATH_HOME = "home"
        private const val WEB_PATH_FEED = "feed"
        private const val WEB_PATH_SETTINGS = "settings"

        private fun getInitialStack(webHistoryPaths: List<String>?, deepLink: DeepLink): List<Config> =
            webHistoryPaths?.takeUnless(List<*>::isEmpty)?.map(::getConfigForPath) ?: getInitialStack(deepLink)

        private fun getInitialStack(deepLink: DeepLink): List<Config> = when (deepLink) {
            is DeepLink.None -> listOf(Config.Home)
            is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
        }

        private fun getPathForConfig(config: Config): String = when (config) {
            Config.Home -> "/$WEB_PATH_HOME"
            Config.Feed -> "/$WEB_PATH_FEED"
            Config.Settings -> "/$WEB_PATH_SETTINGS"
        }

        private fun getConfigForPath(path: String): Config = when (path.removePrefix("/")) {
            WEB_PATH_HOME -> Config.Home
            WEB_PATH_FEED -> Config.Feed
            WEB_PATH_SETTINGS -> Config.Settings
            else -> Config.Home
        }
    }

    sealed interface DeepLink {
        data object None : DeepLink
        class Web(val path: String) : DeepLink
    }

    interface Delegate {
        fun onLogout()
        fun onFeedDetails(id: Long)
    }
}
