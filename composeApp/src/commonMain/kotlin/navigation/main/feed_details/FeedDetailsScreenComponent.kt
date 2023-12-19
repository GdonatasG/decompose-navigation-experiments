package navigation.main.feed_details

import kotlinx.coroutines.flow.StateFlow

interface FeedDetailsScreenComponent {
    val count: StateFlow<Int>

    fun onBack()
}
