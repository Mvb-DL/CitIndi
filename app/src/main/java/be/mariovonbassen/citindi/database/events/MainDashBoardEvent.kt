package be.mariovonbassen.citindi.database.events

sealed interface MainDashBoardEvent{
    data class SetCitySentence(val citySentence: String): MainDashBoardEvent
    object ConfirmCitySentence: MainDashBoardEvent
    object IsMainDashboardLoaded: MainDashBoardEvent

}