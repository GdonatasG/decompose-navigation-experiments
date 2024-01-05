package presentation.navigation.main.settings

import com.arkivanov.decompose.ComponentContext

class DefaultThemeSelectionComponent(
    componentContext: ComponentContext,
    private val onBackCallback: () -> Unit
) : ThemeSelectionComponent, ComponentContext by componentContext {
    override fun onBack() {
        onBackCallback()
    }
}
