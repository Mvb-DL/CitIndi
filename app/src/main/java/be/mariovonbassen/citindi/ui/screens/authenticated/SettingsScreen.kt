package be.mariovonbassen.citindi.ui.screens.authenticated

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import be.mariovonbassen.citindi.ui.components.Header

@Composable
fun SettingsScreen(navController: NavController, currentRoute: String){

    Column {
        Header(navController = navController, currentRoute=currentRoute)
    }

}