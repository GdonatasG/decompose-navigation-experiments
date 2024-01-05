package domain.usecase

import data.DummyDataSource

class DefaultDummyUseCase(
    private val dummyDataSource: DummyDataSource
) : DummyUseCase {
    override suspend fun invoke(): Int = dummyDataSource.getDummyData()
}