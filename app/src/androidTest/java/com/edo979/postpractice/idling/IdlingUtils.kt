package com.edo979.postpractice.idling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

fun <T> Flow<T>.attachIdling(countingIdlingResource: ComposeCountingIdlingResource): Flow<T> =
    transform {
        countingIdlingResource.increment()
        emit(it)
        countingIdlingResource.decrement()
    }