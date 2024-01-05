package navigation.main.settings

import com.arkivanov.decompose.ComponentContext

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    private val onSelectThemeCallback: () -> Unit,
    private val onLogoutCallback: () -> Unit
) : SettingsComponent, ComponentContext by componentContext {

    override fun onSelectTheme() {
        onSelectThemeCallback()
    }

    override fun onLogout() {
        onLogoutCallback()
    }
}
