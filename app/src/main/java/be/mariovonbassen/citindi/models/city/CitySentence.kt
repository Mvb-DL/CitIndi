package be.mariovonbassen.citindi.models.city

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sentences")
class CitySentence (
    val cityId: Int,
    val citySentence: String,
    @PrimaryKey(autoGenerate = true)
    val citySentenceId: Int = 0
)