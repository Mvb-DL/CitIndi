package be.mariovonbassen.citindi.ui.screens.authenticated

import android.net.Uri
import android.util.Log
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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
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
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import be.mariovonbassen.citindi.database.UserDatabase
import be.mariovonbassen.citindi.database.events.AddCityEvent
import be.mariovonbassen.citindi.database.repositories.OfflineCityRepository
import be.mariovonbassen.citindi.database.repositories.OfflineUserRepository
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.ui.MainViewModelFactory
import be.mariovonbassen.citindi.ui.components.Header
import be.mariovonbassen.citindi.ui.components.formatDate
import be.mariovonbassen.citindi.ui.provideAddCityViewModel
import be.mariovonbassen.citindi.ui.states.AddCityState
import be.mariovonbassen.citindi.ui.theme.lightGray
import be.mariovonbassen.citindi.ui.viewmodels.AddCityViewModel
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date
import java.util.Locale


@Composable
fun AddCityScreen(navController: NavController, currentRoute : String,
                  onNavigateToAuthenticatedRoute: () -> Unit) {

    val context = LocalContext.current

    val cityDao = UserDatabase.getDatabase(context).cityDao()
    val cityRepository = OfflineCityRepository(cityDao)

    val userDao = UserDatabase.getDatabase(context).userDao()
    val userRepository = OfflineUserRepository(userDao)

    val viewModelFactory = MainViewModelFactory(userRepository, cityRepository)
    val viewmodel = provideAddCityViewModel(viewModelFactory)

    val state by viewmodel.state.collectAsState()

    //when screen is loaded it loads the cities of the user!
    viewmodel.onUserEvent(AddCityEvent.ScreenLoaded)
    val userCities = state.userCities

    if (state.updatedActiveCity) {
        LaunchedEffect(key1 = true) {
            onNavigateToAuthenticatedRoute.invoke()
        }
    }

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

                ExistingCityDisplay(userCities, viewmodel)

            }

            Spacer(modifier = Modifier.height(100.dp))

            Box(
                modifier = Modifier
            ) {

                AddCityForm(viewmodel = viewmodel, state = state)

            }
        }
    }
}

@Composable
fun ExistingCityDisplay(userCities: List<City>, viewmodel: AddCityViewModel){

        Column(
            modifier = Modifier
                .background(color = Color.White),


        ) {
            LazyRow {
                items(userCities) { city ->
                    StackedCardsAddCity(city, viewmodel)
                }
            }
        }
}

@Composable
fun StackedCardsAddCity(city: City, viewmodel: AddCityViewModel) {

    //change active city on card click

    Card(
        modifier = Modifier
            .width(100.dp)
            .padding(8.dp)
            .fillMaxHeight(0.2f)
            .clickable(onClick = {
                viewmodel.onUserEvent(AddCityEvent.UpdateActiveCity(city.cityId))
            }),

        ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(text = city.cityName, fontSize = 15.sp)
            Spacer(modifier = Modifier.height(8.dp))
            Row {
                Text(text = formatDate(city.arrivalDate))
                Text(text = formatDate(city.leavingDate))
            }
            Text(text = city.gpsPosition)
            Text(text = city.country)

        }
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

        DateField(viewmodel=viewmodel, state=state)

        Spacer(modifier = Modifier.height(20.dp))

        TextField(
            label = { Text(text = "Country") },
            modifier = Modifier
                .width(250.dp),
            value = state.country,
            onValueChange = {
                viewmodel.onUserEvent(AddCityEvent.SetCountry(it))
            })

        Spacer(modifier = Modifier.height(20.dp))

        ImageUploadField()

        Spacer(modifier = Modifier.height(20.dp))
        
        Button(
            modifier = Modifier
                .height(40.dp)
                .width(170.dp),
            colors = ButtonDefaults.buttonColors(containerColor = color),
            onClick = {
                viewmodel.onUserEvent(AddCityEvent.ConfirmAddCity)
            }) {
                    
            Text(text = "Add City", color = Color.White, fontSize = 20.sp)
        }
    }
}


@Composable
fun DateField(viewmodel: AddCityViewModel, state: AddCityState){

    Row(
        modifier = Modifier
            .width(250.dp),
        verticalAlignment = Alignment.CenterVertically
        ) {

        TextField(
            modifier = Modifier
                .width(200.dp)
                    .onFocusChanged {
                       // viewmodel.onUserEvent(AddCityEvent.SetOpenDateField)
                       // viewmodel.onUserEvent(AddCityEvent.SetSurfaceOpacity)
            },
                label = { Text(text = "Add Arrival/Leaving date") },
                value = "",
                onValueChange = {

                })

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(onClick = {
            viewmodel.onUserEvent(AddCityEvent.SetOpenDateField)
            viewmodel.onUserEvent(AddCityEvent.SetSurfaceOpacity)
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

                val currentDate = Date()
                val startDate: Long = currentDate.time
                val oneDayInMillis: Long = 24 * 60 * 60 * 1000
                val endDate: Long = startDate + oneDayInMillis

                val dateRangePickerState = rememberDateRangePickerState(
                    initialSelectedStartDateMillis = startDate,
                    initialSelectedEndDateMillis = endDate
                )

                DateRangePicker(state = dateRangePickerState)

                if (dateRangePickerState.selectedStartDateMillis != null &&
                    dateRangePickerState.selectedEndDateMillis != null
                ) {

                    val selectedArrivalDate: Date =
                        Date(dateRangePickerState.selectedStartDateMillis!!)

                    val selectedLeavingDate: Date =
                        Date(dateRangePickerState.selectedEndDateMillis!!)

                    viewmodel.onUserEvent(
                        AddCityEvent.SetArrivalDate(
                            selectedArrivalDate
                        )
                    )

                    viewmodel.onUserEvent(
                        AddCityEvent.SetLeavingDate(
                            selectedLeavingDate
                        )
                    )
                }
            }
        }

        Button(onClick = {
            viewmodel.onUserEvent(AddCityEvent.SetOpenDateField)
            viewmodel.onUserEvent(AddCityEvent.SetSurfaceOpacity)

        }) {
            Text(text = "Add Date")
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


