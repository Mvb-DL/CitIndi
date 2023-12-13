package be.mariovonbassen.citindi.ui.screens.authenticated


import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import be.mariovonbassen.citindi.navigation.NavigationRoutes
import be.mariovonbassen.citindi.ui.components.DropDownMenu
import be.mariovonbassen.citindi.ui.components.Header
import be.mariovonbassen.citindi.ui.components.HomeButton
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.viewmodels.ProfileViewModel
import be.mariovonbassen.citindi.ui.viewmodels.SignUpViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileScreen(navController: NavController, currentRoute: String
){

    Surface(
        modifier = Modifier
    ) {
        Box{
            Header(navController = navController, currentRoute=currentRoute)
        }

        Box(
            modifier = Modifier
                .padding(0.dp, 30.dp)
        ) {

          /*  ProfileDataDisplay(viewmodel=viewmodel)

            LazyColumn {
                items(entries) { user ->
                    Text(text = "Id: ${user.userId}")
                    Text(text = "Username: ${user.userName}")
                    Text(text = "Password: ${user.password}")
                }
            }*/
        }

        Box(){
            ProfileButton(navController = navController)
        }

    }
}


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ProfileDataDisplay(viewmodel: SignUpViewModel){
    
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(text = "Hey ${viewmodel.state.value.userName}")
    }
    
}

@Composable
fun ProfileButton(navController: NavController) {

    val color = Color(android.graphics.Color.parseColor(blueAppColor))

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            Button(
                onClick = { /*TODO*/ }, modifier = Modifier
                    .width(200.dp),
                shape = RoundedCornerShape(5.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color)
            ) {

                Text(text = "Change Account")

                Spacer(
                    modifier = Modifier
                        .width(15.dp)
                )

                Icon(
                    Icons.Outlined.Face,
                    contentDescription = "change account",
                    tint = Color.White,
                    modifier = Modifier
                        .size(35.dp)
                )
            }

        }

        Spacer(
            modifier = Modifier
                .height(25.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomeButton(navController = navController)
        }

    }
}