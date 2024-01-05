package presentation.navigation.main.home

import com.arkivanov.decompose.ComponentContext

class DefaultHomeComponent(
    componentContext: ComponentContext,
    private val onLogoutCallback: () -> Unit
) : HomeComponent, ComponentContext by componentContext {
    override fun onLogout() {
        onLogoutCallback()
    }
}
