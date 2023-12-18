package be.mariovonbassen.citindi.ui.states

import android.net.Uri
import be.mariovonbassen.citindi.models.city.City
import java.time.LocalDate
import java.util.Date

data class AddCityState(
    val surfaceOpacity: Float = 1f,
    val openDateField: Boolean = false,
    val cityName: String = "",
    val userId: Int = 0,
    val arrivalDate: Date = Date(),
    val leavingDate: Date = Date(),
    val gpsPosition: String = "",
    val country: String = "",
    val cityImage: ByteArray = ByteArray(0),
    val imageURI: Uri? = null,
    val isAddingSuccessful: Boolean = false,
    val userCities: List<City> = emptyList(),
    val updatedActiveCity: Boolean = false
)