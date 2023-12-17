package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveUserState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveUserState
import kotlinx.coroutines.flow.StateFlow


class MainDashBoardViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState
    val globalActiveCityState: StateFlow<ActiveCityState> = GlobalActiveCityState.activeState


}