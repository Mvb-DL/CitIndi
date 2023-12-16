package be.mariovonbassen.citindi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.events.SettingsEvent
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.states.GlobalActiveUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SettingsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState

    fun onUserEvent(event: SettingsEvent) {

        when (event) {

            is SettingsEvent.ConfirmDeleteUser -> {

                viewModelScope.launch {

                    if (globalActiveUserState.value.activeUser != null) {

                        withContext(Dispatchers.IO) {

                            val user = userRepository.getUser(globalActiveUserState.value.activeUser!!.userId)

                            userRepository.deleteUser(user)
                        }

                    }else{
                        Log.d("User is Null", "")
                    }

                }

            }

            is SettingsEvent.LogoutUser -> {

                var globalUserState: ActiveUserState? = ActiveUserState()

                globalUserState = null

            }

        }
    }



}