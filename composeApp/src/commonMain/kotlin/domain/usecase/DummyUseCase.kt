package domain.usecase

interface DummyUseCase {
    suspend operator fun invoke(): Int
}