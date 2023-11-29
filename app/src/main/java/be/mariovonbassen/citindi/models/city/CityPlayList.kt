package be.mariovonbassen.citindi.models.city

import androidx.room.PrimaryKey

data class CityPlayList(
    @PrimaryKey(autoGenerate = true)
    val cityPlayListId: Int = 0
)
