package navigation.main.settings

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface SettingsRootComponent {
    val childStack: Value<ChildStack<*, Config>>

    fun onLogout()

    sealed class Config {
        data class Settings(val component: SettingsComponent) : Config()
        data class ThemeSelection(val component: ThemeSelectionComponent) : Config()
    }
}
