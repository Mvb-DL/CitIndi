package be.mariovonbassen.citindi.database.events

import java.util.Date

sealed interface AddCityEvent{
    data class SetSurfaceOpacity(val surfaceOpacity: Float): AddCityEvent
    data class ReSetSurfaceOpacity(val surfaceOpacity: Float): AddCityEvent
    data class SetOpenDateField(val openDateField: Boolean): AddCityEvent
    data class SetCityName(val cityName: String): AddCityEvent
    data class SetArrivalDate(val arrivalDate: Date): AddCityEvent
    data class SetLeavingDate(val leavingDate: Date): AddCityEvent
    data class SetGpsPosition(val gpsPosition: String): AddCityEvent
    data class SetCountry(val country: String): AddCityEvent
    object ScreenLoaded: AddCityEvent
    object ConfirmAddCity: AddCityEvent
}
