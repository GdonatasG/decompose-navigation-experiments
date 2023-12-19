package navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.replaceAll
import com.arkivanov.decompose.router.stack.webhistory.WebHistoryController
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import navigation.auth.DefaultAuthScreenComponent
import navigation.main.DefaultMainScreenComponent

@OptIn(ExperimentalDecomposeApi::class)
class DefaultRootScreenComponent(
    componentContext: ComponentContext,
    deepLink: DeepLink = DeepLink.None,
    webHistoryController: WebHistoryController? = null,
) : RootScreenComponent, ComponentContext by componentContext {

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
    override val childStack: Value<ChildStack<*, RootScreenComponent.Config>> = stack

    init {
        webHistoryController?.attach(
            navigator = navigation,
            stack = stack,
            getPath = ::getPathForConfig,
            getConfiguration = ::getConfigForPath,
        )
    }

    private fun child(config: Config, componentContext: ComponentContext): RootScreenComponent.Config = when (config) {
        is Config.Auth -> RootScreenComponent.Config.Auth(
            component = DefaultAuthScreenComponent(componentContext = componentContext, onAuthenticatedCallback = {
                navigation.replaceAll(Config.Main)
            })
        )

        is Config.Main -> RootScreenComponent.Config.Main(
            component = DefaultMainScreenComponent(
                componentContext = componentContext,
                onLogoutCallback = {
                    navigation.replaceAll(Config.Auth)
                }
            ))
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Main : Config

        @Serializable
        data object Auth : Config
    }

    private companion object {
        private const val WEB_PATH_MAIN = ""
        private const val WEB_PATH_AUTH = "auth"

        private fun getInitialStack(
            webHistoryPaths: List<String>?, deepLink: DeepLink
        ): List<Config> =
            webHistoryPaths?.takeUnless(List<*>::isEmpty)?.map(::getConfigForPath) ?: getInitialStack(deepLink)

        private fun getInitialStack(deepLink: DeepLink): List<Config> = when (deepLink) {
            is DeepLink.None -> listOf(Config.Auth)
            is DeepLink.Web -> listOf(getConfigForPath(deepLink.path))
        }

        private fun getPathForConfig(config: Config): String = when (config) {
            Config.Main -> ""
            Config.Auth -> "/$WEB_PATH_AUTH"
        }

        private fun getConfigForPath(path: String): Config = when (path.removePrefix("/")) {
            WEB_PATH_MAIN -> Config.Main
            WEB_PATH_AUTH -> Config.Auth
            else -> Config.Auth
        }
    }
}
