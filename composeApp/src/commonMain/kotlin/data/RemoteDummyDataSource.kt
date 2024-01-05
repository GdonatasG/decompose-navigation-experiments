package data

import kotlinx.coroutines.delay

class RemoteDummyDataSource: DummyDataSource {
    override suspend fun getDummyData(): Int {
        delay(3000)
        return 5
    }
}