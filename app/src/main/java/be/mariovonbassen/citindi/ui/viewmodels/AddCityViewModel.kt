package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import be.mariovonbassen.citindi.database.repositories.CityRepository
import be.mariovonbassen.citindi.database.repositories.UserRepository
import be.mariovonbassen.citindi.ui.states.AddCityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import be.mariovonbassen.citindi.database.events.AddCityEvent

class AddCityViewModel(
    private val cityRepository: CityRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AddCityState())
    val state: StateFlow<AddCityState> = _state.asStateFlow()

    fun onUserEvent(event: AddCityEvent) {

        when (event) {

            is AddCityEvent.SetCityName-> {

            }

            is AddCityEvent.SetArrivalDate-> {

            }

            is AddCityEvent.SetLeavingDate-> {

            }

            is AddCityEvent.SetGpsPosition-> {

            }

            is AddCityEvent.SetCountry-> {

            }

            is AddCityEvent.SetSurfaceOpacity-> {
                _state.update {
                    it.copy(surfaceOpacity = 0.4f)
                }
            }

            is AddCityEvent.ReSetSurfaceOpacity-> {
                _state.update {
                    it.copy(surfaceOpacity = 1f)
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

            is AddCityEvent.ConfirmAddCity-> {

            }
        }
    }

    fun setCityName(cityName: String){
        _state.update {
            it.copy(cityName = cityName)
        }
    }

    fun setOpacity(){

    }

    fun resetOpacity(){
    }

    fun closeDateField(){

    }

    fun openDateField(){

    }



}