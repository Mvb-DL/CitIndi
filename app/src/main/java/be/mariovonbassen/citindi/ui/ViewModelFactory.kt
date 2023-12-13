package be.mariovonbassen.citindi.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel


class MainViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {

            return SignUpViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun provideSignUpViewModel(viewModelFactory: MainViewModelFactory): SignUpViewModel {
    val viewModel: SignUpViewModel = viewModel(factory = viewModelFactory)
    return viewModel
}

@Composable
fun provideLoginViewModel(viewModelFactory: MainViewModelFactory): LoginViewModel {
    val viewModel: LoginViewModel = viewModel(factory = viewModelFactory)
    return viewModel
}



