package be.mariovonbassen.citindi.ui.states

import be.mariovonbassen.citindi.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object GlobalActiveUserState {
    private val _activeState = MutableStateFlow(ActiveUserState())
    val activeState: StateFlow<ActiveUserState> = _activeState

    fun updateAppState(newState: ActiveUserState) {
        _activeState.value = newState
    }

}
