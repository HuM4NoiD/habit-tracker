package io.humanoid.habittracker.ui.destinations.entryinput

import io.humanoid.habittracker.datum.model.Entry
import io.humanoid.habittracker.ui.viewmodel.BaseViewModel

sealed class EntryInputUiEvent: BaseViewModel.UiEvent {
    data class AddEntry(val entry: Entry): EntryInputUiEvent()
}
