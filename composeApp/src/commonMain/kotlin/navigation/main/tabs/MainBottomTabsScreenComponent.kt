package navigation.main.tabs

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.main.feed.FeedScreenComponent
import navigation.main.home.HomeScreenComponent
import navigation.main.settings.SettingsScreenRootComponent

interface MainBottomTabsScreenComponent {
    val childStack: Value<ChildStack<*, Tab>>

    fun onHomeTabClicked()
    fun onFeedTabClicked()
    fun onSettingsTabClicked()
    fun onFeedDetails(id: Long)

    sealed class Tab {
        data class Home(val component: HomeScreenComponent) : Tab()
        data class Feed(val component: FeedScreenComponent) : Tab()
        data class Settings(val component: SettingsScreenRootComponent) : Tab()
    }
}
