package data

interface DummyDataSource {
    suspend fun getDummyData(): Int
}