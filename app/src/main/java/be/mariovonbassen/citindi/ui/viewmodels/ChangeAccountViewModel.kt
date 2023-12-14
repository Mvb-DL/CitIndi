package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.events.LoginUserEvent
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.states.ChangeAccountState
import be.mariovonbassen.citindi.ui.states.GlobalActiveUserState
import be.mariovonbassen.citindi.ui.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ChangeAccountViewModel (
    private val userRepository: UserRepository
) : ViewModel() {

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState

    private val _state = MutableStateFlow(ChangeAccountState())
    val state: StateFlow<ChangeAccountState> = _state.asStateFlow()

    fun onUserEvent(event: ChangeAccountEvent) {

        when (event) {

            is ChangeAccountEvent.SetNewUserName -> {
                _state.update {
                    it.copy(
                        userName = event.userName
                    )
                }
            }

            is ChangeAccountEvent.SetNewPassword -> {
                _state.update {
                    it.copy(
                        userPassword = event.userPassword
                    )
                }
            }

            is ChangeAccountEvent.ConfirmChangeAccount -> {

                val userName = state.value.userName
                val userPassword = state.value.userPassword

                viewModelScope.launch {

                    val user = User(
                        userName = userName,
                        password = userPassword,
                    )

                    userRepository.upsertUser(user)

                    //update active User
                    val activeUser = userRepository.getUserByPasswordAndUserName(
                        state.value.userPassword,
                        state.value.userName
                    )

                    val updatedState = ActiveUserState(activeUser = activeUser, isActive = true)

                    GlobalActiveUserState.updateAppState(updatedState)

                }

            }
        }
    }

}