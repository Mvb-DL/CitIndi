package be.mariovonbassen.citindi.ui.viewmodels

import androidx.lifecycle.ViewModel
import be.mariovonbassen.citindi.ui.states.AddCityState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddCityViewModel : ViewModel() {

    // Expose screen UI state
    private val _state = MutableStateFlow(AddCityState())
    val state: StateFlow<AddCityState> = _state.asStateFlow()

    fun setCityName(cityName: String){
        _state.update {
            it.copy(cityName = cityName)
        }
    }

    fun setOpacity(){
        _state.update {
            it.copy(surfaceOpacity = 0.4f)
        }
    }

    fun resetOpacity(){
        _state.update {
            it.copy(surfaceOpacity= 1f)
        }
    }

    fun closeDateField(){
        _state.update {
            it.copy(openDateField = false)
        }
    }

    fun openDateField(){
        _state.update {
            it.copy(openDateField = true)
        }
    }



}