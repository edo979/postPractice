package com.edo979.presentation_common.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

abstract class MviViewModel<T : Any, S : UiState<T>, A : UiAction, E : UiSingleEvent> :
    ViewModel() {

    private val _uiStateFlow: MutableStateFlow<S> by lazy {
        MutableStateFlow(initState())
    }
    val uiStateFlow = _uiStateFlow.onStart {
        uiStateFlowOnStart()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = _uiStateFlow.value
    )

    private val actionFlow: MutableSharedFlow<A> = MutableSharedFlow()
    private val _singleEventFlow = Channel<E>()
    val singleEventFlow = _singleEventFlow.receiveAsFlow()

    init {
        viewModelScope.launch {
            actionFlow.collect {
                handleAction(it)
            }
        }
    }

    abstract fun initState(): S
    abstract fun uiStateFlowOnStart()
    abstract fun handleAction(action: A)

    fun submitAction(action: A) {
        viewModelScope.launch {
            actionFlow.emit(action)
        }
    }

    fun submitState(state: S) {
        viewModelScope.launch {
            _uiStateFlow.value = state
        }
    }

    fun submitSingleEvent(event: E) {
        viewModelScope.launch {
            _singleEventFlow.send(event)
        }
    }
}