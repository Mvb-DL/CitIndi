package be.mariovonbassen.citindi.models.city

import androidx.room.PrimaryKey

data class SightSeeingToDo(
    @PrimaryKey(autoGenerate = true)
    val sightSeeingId: Int = 0
)
