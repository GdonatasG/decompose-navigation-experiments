package navigation.main.home

import com.arkivanov.decompose.ComponentContext

class DefaultHomeScreenComponent(
    componentContext: ComponentContext,
    private val onLogoutCallback: () -> Unit
) : HomeScreenComponent, ComponentContext by componentContext {
    override fun onLogout() {
        onLogoutCallback()
    }
}
