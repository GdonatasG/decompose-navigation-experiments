package navigation.main.settings

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface SettingsScreenRootComponent {
    val childStack: Value<ChildStack<*, Config>>

    fun onLogout()

    sealed class Config {
        data class Settings(val component: SettingsScreenComponent) : Config()
        data class ThemeSelection(val component: ThemeSelectionScreenComponent) : Config()
    }
}
