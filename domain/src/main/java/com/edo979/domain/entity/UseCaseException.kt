package com.edo979.domain.entity

sealed class UseCaseException(cause: Throwable) : Throwable(cause) {

    class PostException(cause: Throwable) : UseCaseException(cause)
    class UserException(cause: Throwable) : UseCaseException(cause)
    class UnknownException(cause: Throwable) : UseCaseException(cause)

    companion object {
        fun createFromThrowable(cause: Throwable): UseCaseException =
            if (cause is UseCaseException) cause
            else UnknownException(cause)
    }
}