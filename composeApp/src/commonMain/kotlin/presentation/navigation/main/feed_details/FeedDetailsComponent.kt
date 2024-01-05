package presentation.navigation.main.feed_details

import kotlinx.coroutines.flow.StateFlow

interface FeedDetailsComponent {
    val count: StateFlow<Int>

    fun onBack()
}
