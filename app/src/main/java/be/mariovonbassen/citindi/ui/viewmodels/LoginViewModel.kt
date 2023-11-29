package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import be.mariovonbassen.citindi.database.events.LoginUserEvent
import be.mariovonbassen.citindi.ui.components.ErrorType
import be.mariovonbassen.citindi.ui.states.LoginErrorState
import be.mariovonbassen.citindi.ui.states.LoginState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel(

) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _errorState = MutableStateFlow(LoginErrorState())
    val errorState: StateFlow<LoginErrorState> = _errorState.asStateFlow()

    fun onUserEvent(event: LoginUserEvent) {

        when (event) {

            is LoginUserEvent.SetUserName -> {
                _state.update {
                    it.copy(
                        userName = event.userName
                    )
                }
            }

            is LoginUserEvent.SetPassword -> {
                _state.update {
                    it.copy(
                        userPassword = event.userPassword
                    )
                }
            }


            is LoginUserEvent.ConfirmLogin -> {

                val inputsValidated = validateLoginInputs()

                if (inputsValidated) {
                    _state.update {
                        it.copy(isLoginSuccessful = true)
                    }
                }

                //userRepository.getUserStream()
            }

        }
    }


    fun resetError(){
        _errorState.update {
            it.copy(isError = false)
        }
    }

    private fun validateLoginInputs(): Boolean {

        val userName = state.value.userName.trim()
        val userPassword = state.value.userPassword.trim()

        return when {

            userName.isEmpty() -> {
                if (errorState.value.errorType == ErrorType.LOGIN_ERROR)
                {
                    _errorState.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Username is empty!"
                        )
                    }
                }
                false
            }

            userPassword.isEmpty() -> {
                if (errorState.value.errorType == ErrorType.LOGIN_ERROR)
                {
                    _errorState.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Password is empty!"
                        )
                    }
                }
                false
            }

            // No errors
            else -> {

                _errorState.update {
                    it.copy(
                        isError = false,
                        errorMessage = ""
                    )
                }

                true
            }
        }

    }
}