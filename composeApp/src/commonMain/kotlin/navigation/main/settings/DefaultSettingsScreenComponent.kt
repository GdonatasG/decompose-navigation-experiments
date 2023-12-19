package navigation.main.settings

import com.arkivanov.decompose.ComponentContext

class DefaultSettingsScreenComponent(
    componentContext: ComponentContext,
    private val onSelectThemeCallback: () -> Unit,
    private val onLogoutCallback: () -> Unit
) : SettingsScreenComponent, ComponentContext by componentContext {

    override fun onSelectTheme() {
        onSelectThemeCallback()
    }

    override fun onLogout() {
        onLogoutCallback()
    }
}
