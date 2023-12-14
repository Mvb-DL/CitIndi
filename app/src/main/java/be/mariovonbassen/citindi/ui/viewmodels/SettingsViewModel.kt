package be.mariovonbassen.citindi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.events.SettingsEvent
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.states.GlobalActiveUserState
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState

    //Delete User

    //Logout User

    fun onUserEvent(event: SettingsEvent) {

        when (event) {

            is SettingsEvent.ConfirmDeleteUser -> {

                viewModelScope.launch {


                    if (globalActiveUserState.value.activeUser != null) {

                        Log.d("1", globalActiveUserState.value.activeUser.toString())

                        userRepository.deleteUser(globalActiveUserState.value.activeUser!!)

                        Log.d("2", globalActiveUserState.value.activeUser.toString())


                        Log.d("3", globalActiveUserState.value.activeUser.toString())

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