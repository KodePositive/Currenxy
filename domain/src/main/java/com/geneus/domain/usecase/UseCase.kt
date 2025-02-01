package com.geneus.domain.usecase

interface UseCase<in Input, out Output> {
    suspend fun execute(input: Input? = null): Output
}