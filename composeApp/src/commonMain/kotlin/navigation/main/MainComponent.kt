package navigation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.backhandler.BackHandler
import navigation.main.feed_details.FeedDetailsComponent
import navigation.main.tabs.MainBottomTabsComponent

interface MainComponent {
    val backHandler: BackHandler
    val childStack: Value<ChildStack<*, Config>>

    fun onBackPressed()

    sealed class Config {
        data class Tabs(val component: MainBottomTabsComponent) : Config()
        data class FeedDetails(val component: FeedDetailsComponent): Config()
    }
}
