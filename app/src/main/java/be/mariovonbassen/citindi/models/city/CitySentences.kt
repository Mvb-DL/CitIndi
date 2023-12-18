package be.mariovonbassen.citindi.models.city

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "sentences")
class CitySentence (
    val cityId: Int,
    val citySentence: String,
    @PrimaryKey(autoGenerate = true)
    val citySentenceId: Int = 0
)