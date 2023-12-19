package navigation.auth

import com.arkivanov.decompose.ComponentContext

class DefaultAuthScreenComponent(
    componentContext: ComponentContext, private val onAuthenticatedCallback: () -> Unit
) : AuthScreenComponent, ComponentContext by componentContext {
    override fun onAuthenticated() {
        onAuthenticatedCallback()
    }
}
