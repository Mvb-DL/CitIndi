package be.mariovonbassen.citindi.models.city.relations

import androidx.room.Embedded
import androidx.room.Relation
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.city.CitySentence

data class CityWithSentences(

    @Embedded val city: City,
    @Relation(
        parentColumn = "cityId",
        entityColumn = "cityId"
    )
    val citySentences: List<CitySentence>
)
