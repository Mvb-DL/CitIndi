package be.mariovonbassen.citindi.ui.screens.unauthenticated



import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import be.mariovonbassen.citindi.database.events.LoginUserEvent
import be.mariovonbassen.citindi.ui.components.AlertMessage
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.viewmodels.LoginViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(viewmodel : LoginViewModel = viewModel(),
                onNavigateToRegistration: () -> Unit,
                onNavigateToForgotPassword: () -> Unit,
                onNavigateToAuthenticatedRoute: () -> Unit) {

    val state by viewmodel.state.collectAsState()
    val errorstate by viewmodel.errorState.collectAsState()
    val color = Color(android.graphics.Color.parseColor(blueAppColor))

    if (state.isLoginSuccessful) {
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        ClickableText(
            text = AnnotatedString("Sign up here"),
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(20.dp),
            onClick = { onNavigateToRegistration.invoke() },
            style = TextStyle(
                fontSize = 14.sp,
                textDecoration = TextDecoration.Underline,
            )
        )
    }

    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        if (errorstate.isError){
            AlertMessage(alertText = errorstate.errorMessage)
        }

        Text(text = "Login", style = TextStyle(fontSize = 40.sp))

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = "Username") },
            value = state.userName,
            onValueChange = { viewmodel.onUserEvent(LoginUserEvent.SetUserName(it)) })

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = "Password") },
            value = state.userPassword,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            onValueChange = { viewmodel.onUserEvent(LoginUserEvent.SetPassword(it)) })

        Spacer(modifier = Modifier.height(20.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {
                    viewmodel.onUserEvent(LoginUserEvent.ConfirmLogin)
                },
                shape = RoundedCornerShape(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .width(50.dp)
            ) {
                Text(text = "Login")
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ClickableText(
            text = AnnotatedString("Forgot password?"),
            onClick = {
                onNavigateToForgotPassword.invoke()
            },
            style = TextStyle(
                fontSize = 14.sp,
            )
        )
    }
}