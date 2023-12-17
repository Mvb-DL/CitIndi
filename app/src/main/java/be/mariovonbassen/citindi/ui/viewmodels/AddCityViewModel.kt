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
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.ActiveUserState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveCityState
import be.mariovonbassen.citindi.ui.states.ActiveStates.GlobalActiveUserState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddCityViewModel(
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddCityState())
    val state: StateFlow<AddCityState> = _state.asStateFlow()

    private val globalActiveUserState: StateFlow<ActiveUserState> = GlobalActiveUserState.activeState

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

            is AddCityEvent.SetOpenDateField-> {

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

            is AddCityEvent.UpdateActiveCity-> {

                viewModelScope.launch {

                    withContext(Dispatchers.IO) {

                        val activeCity = cityRepository.getCityByCityId(event.cityId)

                        val updatedCityState =
                            ActiveCityState(activeCity = activeCity, isActive = true)

                        GlobalActiveCityState.updateCityAppState(updatedCityState)

                        _state.update {
                            it.copy(updatedActiveCity = true)
                        }

                    }

                }
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

                            val latestCity = cityRepository.getLatestCity(userId)

                            val updatedCityState = ActiveCityState(activeCity = latestCity, isActive = true)

                            GlobalActiveCityState.updateCityAppState(updatedCityState)

                            _state.update {
                                it.copy(isAddingSuccessful = true)
                            }

                        }
                    }
               }
            }
        }
    }
}