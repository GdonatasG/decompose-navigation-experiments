package navigation.main.settings

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
import navigation.DeepLink

@OptIn(ExperimentalDecomposeApi::class)
class DefaultSettingsScreenRootComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
    private val onLogoutCallback: () -> Unit
) : SettingsScreenRootComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<Config>()

    private val stack =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialStack = {
                getInitialStack(
                    webHistoryPaths = webHistoryController?.historyPaths,
                    deepLink = deepLink
                )
            },
            handleBackButton = true,
            childFactory = ::child,
        )

    private fun child(config: Config, componentContext: ComponentContext): SettingsScreenRootComponent.Config =
        when (config) {
            is Config.Settings -> SettingsScreenRootComponent.Config.Settings(
                component = DefaultSettingsScreenComponent(
                    componentContext = componentContext,
                    onSelectThemeCallback = {
                        navigation.pushNew(Config.ThemeSelection)
                    },
                    onLogoutCallback = onLogoutCallback
                )
            )

            is Config.ThemeSelection -> SettingsScreenRootComponent.Config.ThemeSelection(
                component = DefaultThemeSelectionScreenComponent(
                    componentContext = componentContext,
                    onBackCallback = {
                        navigation.pop()
                    }
                )
            )
        }

    override val childStack: Value<ChildStack<*, SettingsScreenRootComponent.Config>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private companion object {
        private const val WEB_PATH_SETTINGS = ""
        private const val WEB_PATH_THEME = "theme"

        private fun getInitialStack(webHistoryPaths: List<String>?, deepLink: DeepLink): List<Config> =
            webHistoryPaths
                ?.takeUnless(List<*>::isEmpty)
                ?.map(::getConfigForPath)
                ?: getInitialStack(deepLink)

        private fun getInitialStack(deepLink: DeepLink): List<Config> =
            when (deepLink) {
                is DeepLink.None -> listOf(Config.Settings)
                is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
            }

        private fun getPathForConfig(config: Config): String =
            when (config) {
                Config.Settings -> WEB_PATH_SETTINGS
                Config.ThemeSelection -> "/$WEB_PATH_THEME"
            }

        private fun getConfigForPath(path: String): Config =
            when (path.removePrefix("/")) {
                WEB_PATH_SETTINGS -> Config.Settings
                WEB_PATH_THEME -> Config.ThemeSelection
                else -> Config.Settings
            }
    }


    override fun onLogout() {
        onLogoutCallback()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Settings : Config
        data object ThemeSelection : Config
    }
}
