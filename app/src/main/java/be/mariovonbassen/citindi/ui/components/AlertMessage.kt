package be.mariovonbassen.citindi.ui.components

import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.provideLoginViewModel
import be.mariovonbassen.citindi.ui.provideSignUpViewModel
import be.mariovonbassen.citindi.ui.theme.alarmRed
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AlertMessage(alertText: String) {

    val color = Color(android.graphics.Color.parseColor(alarmRed))

    val context = LocalContext.current
    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val citySentenceDao = UserDatabase.getDatabase(context).citySentenceDao()
    val cityRepository = OfflineCityRepository(cityDao, citySentenceDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)

    val loginViewmodel = provideLoginViewModel(viewModelFactory)
    val signupViewmodel = provideSignUpViewModel(viewModelFactory)

    val showDialog by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

        AlertDialog(
            containerColor = color,
            onDismissRequest = {},
            title = {
                Text(alertText, color= Color.White, fontWeight = FontWeight.SemiBold)
            },
            confirmButton = {},
            modifier = Modifier
        )

        LaunchedEffect(showDialog) {
            coroutineScope.launch {
                delay(2000)
                loginViewmodel.resetError()
                signupViewmodel.resetError()
            }
        }
}
