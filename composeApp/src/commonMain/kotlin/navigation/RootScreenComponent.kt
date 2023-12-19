package navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.auth.AuthScreenComponent
import navigation.main.MainScreenComponent

interface RootScreenComponent {
    val childStack: Value<ChildStack<*, Config>>

    sealed class Config {
        data class Main(val component: MainScreenComponent) : Config()
        data class Auth(val component: AuthScreenComponent) : Config()
    }
}
