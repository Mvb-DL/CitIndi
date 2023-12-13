package be.mariovonbassen.citindi.models.city

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import be.mariovonbassen.citindi.models.User

@Entity
data class City (
    val userId: Int,
    val cityName: String,
    val arrivalDate: String,
    val leavingDate: String,
    val gpsPosition: String,
    val country: String,
    @PrimaryKey(autoGenerate = true)
    val cityId: Int = 0
)