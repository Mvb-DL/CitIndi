package be.mariovonbassen.citindi.ui.screens.authenticated


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import be.mariovonbassen.citindi.navigation.NavigationRoutes
import be.mariovonbassen.citindi.ui.components.DropDownMenu
import be.mariovonbassen.citindi.ui.components.Header
import be.mariovonbassen.citindi.ui.theme.blueAppColor

@Composable
fun ProfileScreen(navController: NavController, currentRoute: String){

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Header(navController = navController, currentRoute = currentRoute)

        ProfileDataDisplay()

    }
}


@Composable
fun ProfileDataDisplay(){

}