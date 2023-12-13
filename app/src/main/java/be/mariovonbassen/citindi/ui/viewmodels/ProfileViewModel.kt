package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.models.User
import kotlinx.coroutines.flow.Flow

class ProfileViewModel(
    name: String
) : ViewModel() {

    var name = name

}