package be.mariovonbassen.citindi.ui.screens.authenticated

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
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
import androidx.activity.compose.setContent
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import androidx.core.view.WindowCompat
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
import be.mariovonbassen.citindi.ui.viewmodels.AddCityViewModel
import coil.compose.rememberImagePainter
import uriToByteArray
import java.util.Date



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

            Spacer(modifier = Modifier.height(50.dp))

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

    val color = Color(android.graphics.Color.parseColor(blueAppColor))

    Card(
        modifier = Modifier
            .width(130.dp)
            .padding(8.dp)
            .fillMaxHeight(0.15f)
            .clickable(onClick = {
                viewmodel.onUserEvent(AddCityEvent.UpdateActiveCity(city.cityId))
            }),

        ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {

        ImageBitmapFromBytes(byteArray = city.cityImage)

        Column(
            modifier = Modifier.padding(10.dp)
        ) {

            Text(modifier = Modifier
                .background(color),
                text = city.cityName, fontSize = 15.sp, fontWeight = FontWeight(600), color= Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            Text( modifier = Modifier
                    .background(color), text = formatDate(city.arrivalDate), fontSize = 12.sp, color= Color.White)

            Spacer(modifier = Modifier.height(8.dp))

            Text(modifier = Modifier
                .background(color), text = city.country, fontSize = 12.sp, color= Color.White)

        }
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

        ImageUploader(viewmodel, state)

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

            DatePickerField(viewmodel= viewmodel, state)

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerField(viewmodel: AddCityViewModel, state: AddCityState) {

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

                val dateRangePickerState = rememberDateRangePickerState(
                    initialSelectedStartDateMillis = state.startDate,
                    initialSelectedEndDateMillis = state.endDate
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
fun ImageUploader(viewmodel: AddCityViewModel, state: AddCityState) {

    var showImage by remember { mutableStateOf(false) }

    val launcher = rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri ->

        if (uri != null) {
            viewmodel.onUserEvent(AddCityEvent.SetUriImage(uri))

        }
    }

    Button(onClick = {
        launcher.launch("image/*")
        showImage = true

    }) {
        Text("Upload Image")
    }

    if (showImage){
        state.imageURI?.let { PreviewImage(uri = it, viewmodel) }
    }
}


@Composable
fun PreviewImage(uri: Uri, viewmodel: AddCityViewModel){

    val context = LocalContext.current

    val painter: Painter = rememberAsyncImagePainter(
        model = uri
    )

    LaunchedEffect(uri) {
        val bytes = uriToByteArray(context, uri)
        if (bytes != null){
            viewmodel.onUserEvent(AddCityEvent.SetCityImage(bytes))
        }
    }

    Image(
        painter = painter,
        contentDescription = null,
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .padding(3.dp),
        contentScale = ContentScale.Fit
    )

}




