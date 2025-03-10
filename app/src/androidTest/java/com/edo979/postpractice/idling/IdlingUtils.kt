package com.edo979.postpractice.idling

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

fun <T> Flow<T>.attachIdling(countingIdlingResource: ComposeCountingIdlingResource): Flow<T> =
    onStart { countingIdlingResource.increment() }.onEach { countingIdlingResource.decrement() }