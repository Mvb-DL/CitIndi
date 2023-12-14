package be.mariovonbassen.citindi.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.viewmodels.ChangeAccountViewModel
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel
import be.mariovonbassen.citindi.ui.viewmodels.MainDashBoardViewModel
import be.mariovonbassen.citindi.ui.viewmodels.ProfileViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel


class MainViewModelFactory(private val userRepository: UserRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        if (modelClass.isAssignableFrom(SignUpViewModel::class.java)) {

            return SignUpViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {

            return LoginViewModel(userRepository) as T
        } else if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {

            return ProfileViewModel(userRepository) as T
        }else if (modelClass.isAssignableFrom(MainDashBoardViewModel::class.java)) {

            return MainDashBoardViewModel(userRepository) as T
        }else if (modelClass.isAssignableFrom(ChangeAccountViewModel::class.java)) {

            return ChangeAccountViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

@Composable
fun provideSignUpViewModel(viewModelFactory: MainViewModelFactory): SignUpViewModel {
    return viewModel(factory = viewModelFactory)
}

@Composable
fun provideLoginViewModel(viewModelFactory: MainViewModelFactory): LoginViewModel {
    return viewModel(factory = viewModelFactory)
}

@Composable
fun provideProfileViewModel(viewModelFactory: MainViewModelFactory): ProfileViewModel {
    return viewModel(factory = viewModelFactory)
}

@Composable
fun provideMainDashBoardViewModel(viewModelFactory: MainViewModelFactory): MainDashBoardViewModel {
    return viewModel(factory = viewModelFactory)
}

@Composable
fun provideChangeAccountViewModel(viewModelFactory: MainViewModelFactory): ChangeAccountViewModel {
    return viewModel(factory = viewModelFactory)
}



