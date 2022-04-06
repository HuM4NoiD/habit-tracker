package io.humanoid.habittracker.ui.viewmodel

import androidx.lifecycle.ViewModel

abstract class BaseViewModel<Event: BaseViewModel.UiEvent>: ViewModel() {

    abstract fun onUiEvent(event: Event)

    interface UiEvent
}