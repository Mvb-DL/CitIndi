package be.mariovonbassen.citindi.models.city

import androidx.room.PrimaryKey

data class CitySightSeeing(
    val cityId: Int,
    val citySightSeeing: String,
    @PrimaryKey(autoGenerate = true)
    val citySightSeeingId: Int = 0
)
