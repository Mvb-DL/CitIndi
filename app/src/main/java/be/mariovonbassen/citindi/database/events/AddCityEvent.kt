package be.mariovonbassen.citindi.database.events

sealed interface AddCityEvent{
    data class SetSurfaceOpacity(val surfaceOpacity: Float): AddCityEvent
    data class ReSetSurfaceOpacity(val surfaceOpacity: Float): AddCityEvent
    data class SetOpenDateField(val openDateField: Boolean): AddCityEvent
    data class SetCityName(val cityName: String): AddCityEvent
    data class SetArrivalDate(val arrivalDate: String): AddCityEvent
    data class SetLeavingDate(val leavingDate: String): AddCityEvent
    data class SetGpsPosition(val gpsPosition: String): AddCityEvent
    data class SetCountry(val country: String): AddCityEvent
    object ConfirmAddCity: AddCityEvent
}
