package be.mariovonbassen.citindi.ui.screens.authenticated

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.components.Footer
import be.mariovonbassen.citindi.ui.provideMainDashBoardViewModel
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.theme.grayShade
import kotlinx.coroutines.flow.StateFlow


@Composable
fun MainDashBoardScreen(navController: NavController) {

    val context = LocalContext.current
    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val cityRepository = OfflineCityRepository(cityDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)
    val viewmodel = provideMainDashBoardViewModel(viewModelFactory)

    val state = viewmodel.globalActiveUserState

    Scaffold(

        bottomBar = {
            Footer(navController = navController)
        }

        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                CenterField()

                Spacer(modifier = Modifier.height(26.dp))

                ToDoDisplay(state = state)

                Spacer(modifier = Modifier.height(26.dp))

                Carousel()

            }
        }
}

@Composable
fun CenterField() {

    val color = Color(android.graphics.Color.parseColor(blueAppColor))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = color)
            //.height(250.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {

            Text(
                modifier = Modifier
                    .padding(10.dp, 20.dp, 0.dp, 0.dp),
                text = "Cityname",
                fontSize = 25.sp,
                fontWeight = FontWeight(600),
                color = Color.White)

            Spacer(modifier = Modifier.height(26.dp))

            DashboardData()

        }
    }
}

@Composable
fun DashboardData() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
    ) {

        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Arrival: 01.09.23", fontSize = 18.sp, color = Color.White, fontWeight = FontWeight(600))

            Spacer(modifier = Modifier
                .height(15.dp))

            Text(text = "Leaving: 01.02.24",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight(600))
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ToDoDisplay(state: StateFlow<ActiveUserState>) {

Column (

) {
    Row {
        Text(text = "Hey ${state.value.activeUser?.userName}", fontSize = 22.sp)

    }
    Row {
        Text(text = "your plan of the day...", fontSize = 16.sp)
    }

}

}

@Composable
fun Carousel() {

    val grayShade = Color(android.graphics.Color.parseColor(grayShade))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = grayShade)
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
    ) {
        
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            
            Column(
                modifier = Modifier.weight(1f)
            ) {
                // First column with two cards stacked
                StackedCards()
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
                // Second column with two cards stacked
                StackedCards()
            }
        }
    }
}

@Composable
fun StackedCards() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp),

    ) {
        // Card content
        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = "1. Card Content")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Additional Content")
        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .height(150.dp),

        ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Add your card content here
            Text(text = "2. Card Content")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Additional Content")
        }
    }
}



