package be.mariovonbassen.citindi.ui.states

data class AddCityState(
    val surfaceOpacity: Float = 1f,
    val openDateField : Boolean = false,
    val cityName: String = ""
)