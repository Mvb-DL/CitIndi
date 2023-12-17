package be.mariovonbassen.citindi.ui.screens.authenticated

import android.annotation.SuppressLint
import android.util.Log
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
import be.mariovonbassen.citindi.database.events.AddCityEvent
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.components.Footer
import be.mariovonbassen.citindi.ui.components.formatDate
import be.mariovonbassen.citindi.ui.provideMainDashBoardViewModel
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveUserState
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.theme.grayShade
import kotlinx.coroutines.flow.StateFlow
import java.util.Date


@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MainDashBoardScreen(navController: NavController) {

    val context = LocalContext.current
    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val cityRepository = OfflineCityRepository(cityDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)
    val viewmodel = provideMainDashBoardViewModel(viewModelFactory)

    val active_user_state = viewmodel.globalActiveUserState
    val active_user_city_state = viewmodel.globalActiveCityState


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

                CenterField(active_user_city_state = active_user_city_state)

                Spacer(modifier = Modifier.height(26.dp))

                ToDoDisplay(active_user_state = active_user_state)

                Spacer(modifier = Modifier.height(26.dp))

                Carousel()

            }
        }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun CenterField(active_user_city_state: StateFlow<ActiveCityState>) {

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

                active_user_city_state.value.activeCity?.let {

                    Row {

                    Text(
                        modifier = Modifier
                            .padding(10.dp, 20.dp, 0.dp, 0.dp),
                        text = it.cityName,
                        fontSize = 25.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White
                    )

                    Text(
                        modifier = Modifier
                            .padding(10.dp, 20.dp, 0.dp, 0.dp),
                        text = "(${it.country})",
                        fontSize = 15.sp,
                        fontWeight = FontWeight(600),
                        color = Color.White
                    )
                    }

                    Spacer(modifier = Modifier.height(26.dp))

                    DashboardData(arrivalDate = it.arrivalDate, leavingDate = it.leavingDate)
                }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DashboardData(arrivalDate: Date, leavingDate: Date) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp, 0.dp, 10.dp, 0.dp)
    ) {

        Row (
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Text(text = "Arrival: ${formatDate(arrivalDate)}",
                fontSize = 18.sp, color = Color.White, fontWeight = FontWeight(600))

            Spacer(modifier = Modifier
                .height(15.dp))

            Text(text = "Leaving: ${formatDate(leavingDate)}",
                fontSize = 18.sp,
                color = Color.White,
                fontWeight = FontWeight(600))
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun ToDoDisplay(active_user_state: StateFlow<ActiveUserState>) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp)
    ) {
        Column(

        ) {
            Row {
                Text(text = "Hey ${active_user_state.value.activeUser?.userName}", fontSize = 26.sp)

            }
            Row {
                Text(text = "your plan of the day...", fontSize = 16.sp)
            }

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
                StackedCards()
            }

            Column(
                modifier = Modifier.weight(1f)
            ) {
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

            Text(text = "Important Sentences")
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
            Text(text = "Sightseeing To DO")
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Additional Content")
        }
    }
}



