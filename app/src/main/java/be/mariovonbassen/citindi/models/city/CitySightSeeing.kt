package be.mariovonbassen.citindi.models.city

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "city_sightseeing")
data class CitySightSeeing(
    val cityId: Int,
    val citySightSeeing: String,
    @PrimaryKey(autoGenerate = true)
    val citySightSeeingId: Int = 0
)
