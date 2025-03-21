package com.edo979.postpractice.idling

import androidx.compose.ui.test.IdlingResource
import androidx.test.espresso.idling.CountingIdlingResource

class ComposeCountingIdlingResource(name: String) : IdlingResource {

    private val countingIdlingResource = CountingIdlingResource(name, true)
    override val isIdleNow: Boolean
        get() = countingIdlingResource.isIdleNow


    fun increment() = countingIdlingResource.increment()
    fun decrement() = countingIdlingResource.decrement()
}