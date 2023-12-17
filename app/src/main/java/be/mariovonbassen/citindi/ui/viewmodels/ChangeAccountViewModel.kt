package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveUserState
import be.mariovonbassen.citindi.ui.states.ChangeAccountState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveUserState
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

                val activeUser = globalActiveUserState.value.activeUser

                if (activeUser != null) {

                    viewModelScope.launch {

                        val user = userRepository.getUserByPasswordAndUserName(
                            activeUser.password,
                            activeUser.userName
                        )

                        val newUserName = state.value.userName
                        val newUserPassword = state.value.userPassword

                        //Check if data is empty and keep old state data
                        if (newUserName != "" || newUserPassword != "") {

                            user.apply {
                                userName = newUserName
                                password = newUserPassword
                            }

                            userRepository.upsertUser(user)

                            //update active User
                            val updatedState = ActiveUserState(activeUser = user, isActive = true)

                            GlobalActiveUserState.updateAppState(updatedState)

                        }
                    }
                }
            }
        }
    }
}

