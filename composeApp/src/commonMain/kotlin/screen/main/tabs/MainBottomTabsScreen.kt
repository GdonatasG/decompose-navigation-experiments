package screen.main.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.main.tabs.MainBottomTabsComponent
import screen.main.FeedScreen
import screen.main.HomeScreen
import screen.main.settings.SettingsRootScreen

@Composable
fun MainBottomTabsScreen(component: MainBottomTabsComponent, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = MaterialTheme.colorScheme.background) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top + WindowInsetsSides.Horizontal)),
        ) {
            Children(
                stack = component.childStack,
                modifier = Modifier.weight(1f),
                animation = stackAnimation(fade())
            ) {
                when (val child = it.instance) {
                    is MainBottomTabsComponent.Tab.Home -> HomeScreen(child.component)
                    is MainBottomTabsComponent.Tab.Feed -> FeedScreen(child.component)
                    is MainBottomTabsComponent.Tab.Settings -> SettingsRootScreen(child.component)
                }
            }
            BottomBar(component = component, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun BottomBar(
    component: MainBottomTabsComponent,
    modifier: Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    NavigationBar(modifier = modifier) {
        NavigationBarItem(
            selected = activeComponent is MainBottomTabsComponent.Tab.Home,
            onClick = component::onHomeTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
        )
        NavigationBarItem(
            selected = activeComponent is MainBottomTabsComponent.Tab.Feed,
            onClick = component::onFeedTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Feed"
                )
            }
        )
        NavigationBarItem(
            selected = activeComponent is MainBottomTabsComponent.Tab.Settings,
            onClick = component::onSettingsTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Settings"
                )
            }
        )
    }

}
