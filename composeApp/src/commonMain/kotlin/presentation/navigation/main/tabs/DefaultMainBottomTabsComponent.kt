package presentation.navigation.main.tabs

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import presentation.navigation.main.feed.DefaultFeedComponent
import presentation.navigation.main.home.DefaultHomeComponent
import presentation.navigation.main.settings.DefaultSettingsRootComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultMainBottomTabsComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
    private val delegate: Delegate
) : MainBottomTabsComponent, ComponentContext by componentContext {

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

    override val childStack: Value<ChildStack<*, MainBottomTabsComponent.Tab>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = Companion::getPathForConfig,
            getConfiguration = Companion::getConfigForPath,
        )
    }

    private fun logout() {
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

    private fun child(config: Config, componentContext: ComponentContext): MainBottomTabsComponent.Tab =
        when (config) {
            is Config.Home -> MainBottomTabsComponent.Tab.Home(
                component = DefaultHomeComponent(
                    componentContext = componentContext,
                    onLogoutCallback = { logout() })
            )

            is Config.Feed -> MainBottomTabsComponent.Tab.Feed(
                component = DefaultFeedComponent(componentContext = componentContext,
                    delegate = object : DefaultFeedComponent.Delegate {
                        override fun onLogout() {
                            logout()
                        }

                        override fun onDetails(id: Long) {
                            delegate.onFeedDetails(id = id)
                        }

                    })
            )

            is Config.Settings -> MainBottomTabsComponent.Tab.Settings(
                component = DefaultSettingsRootComponent(
                    componentContext = componentContext,
                    onLogoutCallback = { logout() })
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
            webHistoryPaths?.takeUnless(List<*>::isEmpty)?.map(Companion::getConfigForPath) ?: getInitialStack(deepLink)

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
