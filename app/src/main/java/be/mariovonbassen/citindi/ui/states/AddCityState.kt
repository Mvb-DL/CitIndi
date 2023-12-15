package be.mariovonbassen.citindi.ui.states

data class AddCityState(
    val surfaceOpacity: Float = 1f,
    val openDateField : Boolean = false,
    val cityName: String = "",
    val userId: Int = 0,
    val arrivalDate: String = "",
    val leavingDate: String = "",
    val gpsPosition: String = "",
    val country: String = "",
    val isAddingSuccessful: Boolean = false
)