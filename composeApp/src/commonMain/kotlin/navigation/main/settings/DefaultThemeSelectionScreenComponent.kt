package navigation.main.settings

import com.arkivanov.decompose.ComponentContext

class DefaultThemeSelectionScreenComponent(
    componentContext: ComponentContext,
    private val onBackCallback: () -> Unit
) : ThemeSelectionScreenComponent, ComponentContext by componentContext {
    override fun onBack() {
        onBackCallback()
    }
}
