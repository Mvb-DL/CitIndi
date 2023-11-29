package be.mariovonbassen.citindi.ui.screens.authenticated

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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import be.mariovonbassen.citindi.ui.components.Footer
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import be.mariovonbassen.citindi.ui.theme.grayShade


@Composable
fun MainDashBoardScreen(navController: NavController) {

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

                ToDoDisplay()

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

@Composable
fun ToDoDisplay() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {

        Text(text = "Plan of the day...", fontSize = 22.sp)

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



