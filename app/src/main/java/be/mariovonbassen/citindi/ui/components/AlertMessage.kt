package be.mariovonbassen.citindi.ui.components



import androidx.compose.foundation.background
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun AlertMessage(alertText: String, viewmodelSignUp : SignUpViewModel = viewModel(), viewmodelLogin : LoginViewModel = viewModel() ) {

    val showDialog by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(alertText)
            },
            confirmButton = {},
            modifier = Modifier
                .background(Color.Red)
        )

        LaunchedEffect(showDialog) {
            coroutineScope.launch {
                delay(2000)
                //TODO
                viewmodelSignUp.resetError()
                viewmodelLogin.resetError()
            }
        }
}
