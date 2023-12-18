package be.mariovonbassen.citindi.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import be.mariovonbassen.citindi.models.city.CitySentence


@Dao
interface CitySentenceDao {
    @Insert
    suspend fun insertCitySentences(sentences: List<CitySentence>)
    @Query("SELECT * FROM sentences WHERE cityId = :cityId")
    suspend fun getSentencesForCity(cityId: Int): List<CitySentence>

}