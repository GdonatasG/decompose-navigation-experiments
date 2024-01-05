package di

import data.DummyDataSource
import data.RemoteDummyDataSource
import domain.usecase.DefaultDummyUseCase
import domain.usecase.DummyUseCase
import org.koin.dsl.module

fun appModule() = listOf(dataSourceModule, useCaseModule)

private val dataSourceModule = module {
    single<DummyDataSource> {
        RemoteDummyDataSource()
    }
}

private val useCaseModule = module {
    single<DummyUseCase> {
        DefaultDummyUseCase(
            dummyDataSource = get<DummyDataSource>()
        )
    }
}
