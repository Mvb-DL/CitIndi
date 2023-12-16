package be.mariovonbassen.citindi.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import be.mariovonbassen.citindi.database.repositories.CityRepository
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.AddCityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import be.mariovonbassen.citindi.database.events.AddCityEvent
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.ui.states.ActiveUserState
import be.mariovonbassen.citindi.ui.states.GlobalActiveUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddCityViewModel(
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddCityState())
    val state: StateFlow<AddCityState> = _state.asStateFlow()

    val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState


    fun onUserEvent(event: AddCityEvent) {

        when (event) {

            is AddCityEvent.ScreenLoaded -> {

                viewModelScope.launch {

                    withContext(Dispatchers.IO) {

                        val userId = globalActiveUserState.value.activeUser?.userId

                        if (userId != null) {

                            val userCities = cityRepository.getCitiesByUserId(userId)

                            _state.update {
                                it.copy(userCities = userCities)
                            }
                        }

                    }
                }
            }

            is AddCityEvent.SetCityName-> {
                _state.update {
                    it.copy(cityName = event.cityName)
                }
            }

            is AddCityEvent.SetArrivalDate-> {
                _state.update {
                    it.copy(arrivalDate = event.arrivalDate)
                }
            }

            is AddCityEvent.SetLeavingDate-> {
                _state.update {
                    it.copy(leavingDate = event.leavingDate)
                }
            }

            is AddCityEvent.SetGpsPosition-> {
                _state.update {
                    it.copy(gpsPosition = event.gpsPosition)
                }
            }

            is AddCityEvent.SetCountry-> {
                _state.update {
                    it.copy(country = event.country)
                }
            }

            is AddCityEvent.SetSurfaceOpacity-> {
                _state.update {
                    it.copy(surfaceOpacity = 0.4f)
                }
            }

            is AddCityEvent.ReSetSurfaceOpacity-> {

            }

            is AddCityEvent.SetOpenDateField-> {

            }

            is AddCityEvent.ConfirmAddCity-> {

                val cityName = state.value.cityName
                val arrivalDate = state.value.arrivalDate
                val leavingDate = state.value.leavingDate
                val gpsPosition = state.value.gpsPosition
                val country = state.value.country
                val userId = globalActiveUserState.value.activeUser?.userId

                //validate input

                viewModelScope.launch {

                    if (userId != null) {

                        withContext(Dispatchers.IO) {

                            val city = City(
                                userId, cityName, arrivalDate,
                                leavingDate, gpsPosition, country
                            )

                            cityRepository.upsertCity(city)

                            _state.update {
                                it.copy(isAddingSuccessful = true)
                            }

                        }

                    }

                }

            }
        }
    }

    fun setOpacity(){

        if (state.value.surfaceOpacity == 1.0f) {
            _state.update {
                it.copy(surfaceOpacity = 0.4f)
            }
            }else{
                _state.update {
                    it.copy(surfaceOpacity = 1.0f)
                }
            }

    }

    fun handleDateField(){
        if(state.value.openDateField){
            _state.update {
                it.copy(openDateField = false)
            }
        }else{
            _state.update {
                it.copy(openDateField = true)
            }
        }
    }


}