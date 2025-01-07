package com.geneus.currenxy.domain.usecase

interface UseCase<in Input, out Output> {
    suspend fun execute(input: Input): Output
}