package be.mariovonbassen.citindi.database.events

import be.mariovonbassen.citindi.models.city.CitySentence

sealed interface MainDashBoardEvent{
    data class SetCitySentence(val citySentence: String): MainDashBoardEvent
    data class ConfirmCitySentence(val citySentenceList: List<CitySentence>) : MainDashBoardEvent
    object IsMainDashboardLoaded: MainDashBoardEvent

}