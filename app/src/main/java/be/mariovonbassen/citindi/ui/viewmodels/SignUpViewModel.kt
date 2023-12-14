package be.mariovonbassen.citindi.ui.viewmodels


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.SignUpUserEvent
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.ui.components.ErrorType
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.states.RegistrationErrorState
import be.mariovonbassen.citindi.ui.states.SignUpState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignUpViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(SignUpState())
    val state: StateFlow<SignUpState> = _state.asStateFlow()

    private val _errorState = MutableStateFlow(RegistrationErrorState())
    val errorState: StateFlow<RegistrationErrorState> = _errorState.asStateFlow()


    fun onUserEvent(event: SignUpUserEvent) {

        when(event) {

            is SignUpUserEvent.SetUserName -> {
                _state.update { it.copy(
                    userName = event.userName
                ) }

            }

            is SignUpUserEvent.SetPassword -> {
                _state.update { it.copy(
                    userPassword = event.userPassword
                ) }
            }

            is SignUpUserEvent.SetPasswordConfirm -> {
                _state.update { it.copy(
                    userPasswordConfirm = event.userPasswordConfirm
                ) }
            }

            is SignUpUserEvent.ConfirmSignUp -> {

                val inputsValidated = validateSignUpInputs()

                if (inputsValidated) {
                    _state.update {
                        it.copy(isRegistrationSuccessful = true)
                    }
                }
            }

            SignUpUserEvent.SaveUser -> {

                val userName = state.value.userName
                val userPassword = state.value.userPassword

                val inputsValidated = validateSignUpInputs()

                if (inputsValidated) {

                    _state.update {
                        it.copy(isRegistrationSuccessful = true)
                    }

                    val user = User(
                        userName = userName,
                        password = userPassword,
                    )


                    viewModelScope.launch {
                       userRepository.upsertUser(user)
                    }

                }

            }
        }
    }


    fun resetError(){
        _errorState.update {
            it.copy(isError = false)
        }
    }


    private fun validateSignUpInputs(): Boolean {

        val userName = state.value.userName.trim()
        val userPassword = state.value.userPassword.trim()
        val userPasswordConfirm = state.value.userPasswordConfirm.trim()

        return when {

            userName.isEmpty() -> {
                if (errorState.value.errorType == ErrorType.REGISTRATION_ERROR)
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
                if (errorState.value.errorType == ErrorType.REGISTRATION_ERROR)
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

            userPasswordConfirm.isEmpty() -> {
                if (errorState.value.errorType == ErrorType.REGISTRATION_ERROR)
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

            // Password and Confirm Password are different
            userPassword != userPasswordConfirm -> {

                if (errorState.value.errorType == ErrorType.REGISTRATION_ERROR)

                {
                    _errorState.update {
                        it.copy(
                            isError = true,
                            errorMessage = "Passwords are not similar!"
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