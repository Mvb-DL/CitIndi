package be.mariovonbassen.citindi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.events.MainDashBoardEvent
import be.mariovonbassen.citindi.database.repositories.CityRepository
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.models.city.CitySentence
import be.mariovonbassen.citindi.models.city.relations.CityWithSentences
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveUserState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveUserState
import be.mariovonbassen.citindi.ui.states.AddCityState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainDashBoardViewModel(
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState
    val globalActiveCityState: StateFlow<ActiveCityState> = GlobalActiveCityState.activeState

    private val _state = MutableStateFlow(AddCityState())
    val state: StateFlow<AddCityState> = _state.asStateFlow()

    private var _citySentenceList = MutableLiveData<List<CitySentence>>(state.value.citySentenceList)
    var citySentenceList: LiveData<List<CitySentence>> = _citySentenceList

    val cityId = globalActiveCityState.value.activeCity?.cityId

    init {

        if (cityId != null) {
            loadData(cityId)
        }
    }

    private fun loadData(cityId: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val citySentences = cityRepository.getCitySentencesForCity(cityId)
                _citySentenceList.postValue(citySentences)

            }
        }
    }

    fun onUserEvent(event: MainDashBoardEvent) {

        when (event) {

            is MainDashBoardEvent.SetCitySentence -> {

                _state.update {
                    it.copy(
                        citySentence = event.citySentence
                    )
                }


            }

            is MainDashBoardEvent.ConfirmCitySentence -> {

                suspend fun addCitySentence(cityId: Int, sentence: String) {
                    val newCitySentence = CitySentence(cityId, sentence) // Assuming other attributes or IDs are handled accordingly
                    cityRepository.insertCitySentence(newCitySentence)
                    val citySentences = cityRepository.getCitySentencesForCity(cityId)
                    _citySentenceList.value = citySentences
                }

                viewModelScope.launch {


                    if (state.value.citySentence != null) {
                        if (cityId != null) {
                            addCitySentence(cityId, state.value.citySentence)
                        }
                    }
                }

            }



            is MainDashBoardEvent.IsMainDashboardLoaded -> {


            }
        }

    }




}