package presentation.navigation.main.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import presentation.navigation.main.feed.FeedComponent
import presentation.navigation.main.home.HomeComponent
import presentation.navigation.main.settings.SettingsRootComponent

interface MainBottomTabsComponent {
    val childStack: Value<ChildStack<*, Tab>>

    fun onHomeTabClicked()
    fun onFeedTabClicked()
    fun onSettingsTabClicked()
    fun onFeedDetails(id: Long)

    sealed class Tab {
        data class Home(val component: HomeComponent) : Tab()
        data class Feed(val component: FeedComponent) : Tab()
        data class Settings(val component: SettingsRootComponent) : Tab()
    }
}
