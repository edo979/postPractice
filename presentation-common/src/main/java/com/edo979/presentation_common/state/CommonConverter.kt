package com.edo979.presentation_common.state


abstract class CommonConverter<T : Any, R : Any> {

    fun convert(result: Result<T>) = result.fold(
        onSuccess = { UiState.Success(convertSuccess(it)) },
        onFailure = { UiState.Error(errorMessage = it.localizedMessage.orEmpty()) })

    abstract fun convertSuccess(data: T): R
}