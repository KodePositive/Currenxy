package com.geneus.currenxy.domain.usecase

internal interface UseCase<in Input, out Output> {
    suspend fun execute(input: Input? = null): Output
}