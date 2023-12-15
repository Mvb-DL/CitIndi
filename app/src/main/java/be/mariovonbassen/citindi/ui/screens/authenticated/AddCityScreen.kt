package be.mariovonbassen.citindi.ui.screens.authenticated

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import be.mariovonbassen.citindi.ui.theme.blueAppColor
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.*
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import androidx.compose.material3.DatePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.events.AddCityEvent
import be.mariovonbassen.citindi.database.events.ChangeAccountEvent
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.components.Header
import be.mariovonbassen.citindi.ui.provideAddCityViewModel
import be.mariovonbassen.citindi.ui.provideChangeAccountViewModel
import be.mariovonbassen.citindi.ui.states.AddCityState
import be.mariovonbassen.citindi.ui.theme.lightGray
import be.mariovonbassen.citindi.ui.viewmodels.AddCityViewModel


@Composable
fun AddCityScreen(navController: NavController, currentRoute : String) {

    val context = LocalContext.current

    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val cityRepository = OfflineCityRepository(cityDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)
    val viewmodel = provideAddCityViewModel(viewModelFactory)

    val state by viewmodel.state.collectAsState()

    Surface(
        modifier = Modifier
            .alpha(state.surfaceOpacity)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(){

                Header(navController = navController, currentRoute=currentRoute)

            }

            Spacer(modifier = Modifier.height(40.dp))

            Box(
                modifier = Modifier
            ) {

                ExistingCityDisplay()

            }

            Spacer(modifier = Modifier.height(200.dp))

            Box(
                modifier = Modifier
            ) {

                AddCityForm(viewmodel = viewmodel, state = state)

            }
        }
    }
}

@Composable
fun ExistingCityDisplay(){

        Column(
            modifier = Modifier
                .background(color = Color.White)
                .fillMaxWidth()
        ) {

            Text(text = "Existing Cities")
        }
}


@Composable
fun AddCityForm(viewmodel: AddCityViewModel, state: AddCityState) {

    val color = Color(android.graphics.Color.parseColor(blueAppColor))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TextField(
            label = { Text(text = "City Name") },
            modifier = Modifier
                .width(250.dp),
            value = state.cityName,
            onValueChange = {
                viewmodel.onUserEvent(AddCityEvent.SetCityName(it))
            })

        Spacer(modifier = Modifier.height(20.dp))

        DateFields(viewmodel=viewmodel, state=state)

        Spacer(modifier = Modifier.height(20.dp))

        ImageUploadField()

        Spacer(modifier = Modifier.height(20.dp))
        
        Button(
            modifier = Modifier
                .height(40.dp)
                .width(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            onClick = {}) {
                    
            Text(text = "Add City", color = Color.White, fontSize = 20.sp)
        }
    }

}


@Composable
fun DateFields(viewmodel: AddCityViewModel, state: AddCityState){

    Row(
        modifier = Modifier
            .width(250.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {

        TextField(
            modifier = Modifier
                .width(200.dp),
                label = { Text(text = "Add Arrival date") },
                value = "",
                onValueChange = {
                    viewmodel.onUserEvent(AddCityEvent.SetArrivalDate(it))
                })

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
                viewmodel.handleDateField()
                viewmodel.setOpacity()
        }) {
            Icon(
                    Icons.Outlined.DateRange,
                    contentDescription = "date change",
                    tint  = Color.Black,
                    modifier = Modifier
                        .size(35.dp)
                )
            }

        if (state.openDateField){

            DatePickerField(viewmodel= viewmodel)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(viewmodel: AddCityViewModel) {

    Dialog(onDismissRequest = { }) {

        Card(
            modifier = Modifier
                .fillMaxWidth(1f)
                .fillMaxHeight(0.8f)
                .padding(5.dp),
            elevation = CardDefaults.cardElevation(
                    defaultElevation = 10.dp
                ),
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {


                    // Pre-select a date for January 4, 2020
                    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = 1578096000000)
                    DatePicker(state = datePickerState, modifier = Modifier.padding(16.dp))

                    Text("Selected date timestamp: ${datePickerState.selectedDateMillis ?: "no selection"}")

                    Button(onClick = {
                        viewmodel.setOpacity()
                        viewmodel.handleDateField()
                    }) {
                        Text(text = "Add Date")
                    }
                }
        }
    }
}


@Composable
fun ImageUploadField(){

    val color = Color(android.graphics.Color.parseColor(lightGray))


    //The URI of the photo that the user has picked
    var photoUri: Uri? by remember { mutableStateOf(null) }

    //The launcher we will use for the PickVisualMedia contract.
    //When .launch()ed, this will display the photo picker.
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        //When the user has selected a photo, its URI is returned here
        photoUri = uri
    }

    Column(
        modifier = Modifier
            .width(250.dp)
    ) {

        Card(
            colors = CardDefaults.cardColors(
                containerColor = color
            ),

        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                colors = ButtonDefaults.buttonColors(containerColor = color),
                onClick = {
                    //On button press, launch the photo picker
                    launcher.launch(
                        PickVisualMediaRequest(
                            //Here we request only photos. Change this to .ImageAndVideo if you want videos too.
                            //Or use .VideoOnly if you only want videos.
                            mediaType = ActivityResultContracts.PickVisualMedia.ImageOnly
                        )
                    )
                }
            ) {
                Text("Select Photo", color = Color.Black, fontSize = 20.sp)
            }

        }

        if (photoUri != null) {
            //Use Coil to display the selected image
            val painter = rememberAsyncImagePainter(
                ImageRequest
                    .Builder(LocalContext.current)
                    .data(data = photoUri)
                    .build()
            )

            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .padding(5.dp)
                    .fillMaxWidth()
                    .border(6.0.dp, Color.Gray),
                contentScale = ContentScale.Crop
            )
        }
    }
}


