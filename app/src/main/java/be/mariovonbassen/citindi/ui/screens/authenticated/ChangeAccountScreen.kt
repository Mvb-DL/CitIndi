package be.mariovonbassen.citindi.ui.screens.authenticated

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.events.LoginUserEvent
import be.mariovonbassen.citindi.database.events.SignUpUserEvent
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.navigation.NavigationRoutes
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.components.Header
import be.mariovonbassen.citindi.ui.provideChangeAccountViewModel
import be.mariovonbassen.citindi.ui.provideProfileViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ChangeAccountScreen(navController: NavController, currentRoute: String){

    val context = LocalContext.current
    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val citySentenceDao = UserDatabase.getDatabase(context).citySentenceDao()
    val cityRepository = OfflineCityRepository(cityDao, citySentenceDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)
    val viewmodel = provideChangeAccountViewModel(viewModelFactory)

    val active_user_state = viewmodel.globalActiveUserState

    val state by viewmodel.state.collectAsState()

    Surface(
        modifier = Modifier
    ) {
        Box {
            Header(navController = navController, currentRoute = currentRoute)
        }

        Column() {

            Text(text = "${active_user_state.value.activeUser?.userName}")

            TextField(
                label = { Text(text = "Enter new Username") },
                value = state.userName,
                onValueChange = {
                    viewmodel.onUserEvent(ChangeAccountEvent.SetNewUserName(it))
                })

            Spacer(modifier = Modifier.height(20.dp))

            TextField(
                label = { Text(text = "Enter new Password") },
                value = state.userPassword,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                onValueChange = {
                    viewmodel.onUserEvent(ChangeAccountEvent.SetNewPassword(it))
                })

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    viewmodel.onUserEvent(ChangeAccountEvent.ConfirmChangeAccount)
                    navController.navigate(NavigationRoutes.Authenticated.ProfileScreen.route)
                }
            ){
                Text(text = "Change Account")
            }

        }

    }

}