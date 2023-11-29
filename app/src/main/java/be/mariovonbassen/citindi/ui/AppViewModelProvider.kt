package be.mariovonbassen.citindi.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import be.mariovonbassen.citindi.CitIndiApplication
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel

object AppViewModelProvider {

    val Factory = viewModelFactory {

        initializer {
            SignUpViewModel(
                citindiApplication().container.userRepository
            )
        }
    }
}

fun CreationExtras.citindiApplication(): CitIndiApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as CitIndiApplication)