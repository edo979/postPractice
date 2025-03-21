package com.edo979.domain.usecase

import com.edo979.domain.entity.UseCaseException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

abstract class UseCase<I : UseCase.Request, O : UseCase.Response>(private val configuration: Configuration) {

    fun execute(request: I): Flow<Result<O>> = process(request)
        .map { Result.success(it) }
        .flowOn(configuration.dispatcher)
        .catch { emit(Result.failure(UseCaseException.Companion.createFromThrowable(it))) }

    abstract fun process(request: I): Flow<O>

    class Configuration(val dispatcher: CoroutineDispatcher)
    interface Request
    interface Response
}