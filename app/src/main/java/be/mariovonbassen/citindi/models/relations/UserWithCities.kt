package be.mariovonbassen.citindi.models.relations

import androidx.room.Embedded
import androidx.room.Relation
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City

data class UserWithCities (
    @Embedded val user: User,
    @Relation(
        parentColumn = "userId",
        entityColumn = "userId"
    )

    val cities: List<City>
)
