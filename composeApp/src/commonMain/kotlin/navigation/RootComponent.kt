package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.auth.AuthScreenComponent
import navigation.main.MainComponent

interface RootComponent {
    val childStack: Value<ChildStack<*, Config>>

    sealed class Config {
        data class Main(val component: MainComponent) : Config()
        data class Auth(val component: AuthScreenComponent) : Config()
    }
}
