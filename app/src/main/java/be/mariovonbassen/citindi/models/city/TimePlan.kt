package be.mariovonbassen.citindi.models.city

import androidx.room.PrimaryKey

data class TimePlan(
    @PrimaryKey(autoGenerate = true)
    val timePlanId: Int = 0
)

