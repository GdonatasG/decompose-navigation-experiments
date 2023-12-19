package navigation.main

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import navigation.main.feed_details.FeedDetailsScreenComponent
import navigation.main.tabs.MainBottomTabsScreenComponent

interface MainScreenComponent {
    val childStack: Value<ChildStack<*, Config>>

    sealed class Config {
        data class Tabs(val component: MainBottomTabsScreenComponent) : Config()
        data class FeedDetails(val component: FeedDetailsScreenComponent): Config()
    }
}
