package screen.main.tabs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import navigation.main.tabs.MainBottomTabsScreenComponent
import screen.main.FeedScreen
import screen.main.HomeScreen
import screen.main.settings.SettingsRootScreen

@Composable
fun MainBottomTabsScreen(component: MainBottomTabsScreenComponent, modifier: Modifier = Modifier) {
    Surface(modifier = modifier, color = MaterialTheme.colors.background) {
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
                    is MainBottomTabsScreenComponent.Tab.Home -> HomeScreen(child.component)
                    is MainBottomTabsScreenComponent.Tab.Feed -> FeedScreen(child.component)
                    is MainBottomTabsScreenComponent.Tab.Settings -> SettingsRootScreen(child.component)
                }
            }
            BottomBar(component = component, modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
private fun BottomBar(
    component: MainBottomTabsScreenComponent,
    modifier: Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeComponent = childStack.active.instance

    BottomNavigation(modifier = modifier) {
        BottomNavigationItem(
            selected = activeComponent is MainBottomTabsScreenComponent.Tab.Home,
            onClick = component::onHomeTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Home"
                )
            }
        )
        BottomNavigationItem(
            selected = activeComponent is MainBottomTabsScreenComponent.Tab.Feed,
            onClick = component::onFeedTabClicked,
            icon = {
                Icon(
                    imageVector = Icons.Default.List,
                    contentDescription = "Feed"
                )
            }
        )
        BottomNavigationItem(
            selected = activeComponent is MainBottomTabsScreenComponent.Tab.Settings,
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
