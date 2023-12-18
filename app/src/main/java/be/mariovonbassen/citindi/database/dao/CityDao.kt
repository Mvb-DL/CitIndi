package be.mariovonbassen.citindi.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.city.CitySentence
import be.mariovonbassen.citindi.models.city.relations.CityWithSentences

@Dao
interface CityDao {
    @Upsert
    suspend fun upsertCity(city: City)
    @Delete
    suspend fun deleteCity(city: City)
    @Query("SELECT * FROM cities WHERE userId = :userId")
    fun getCitiesByUserId(userId: Int): List<City>
    @Query("SELECT * FROM cities WHERE cityId = :cityId")
    fun getCityByCityId(cityId: Int): City
    @Query("SELECT * FROM cities WHERE userId = :userId ORDER BY cityId DESC LIMIT 1")
    suspend fun getLatestCity(userId: Int): City
    @Transaction
    @Query("SELECT * FROM sentences WHERE cityId = :cityId")
    fun getCitySentencesByCityId(cityId: Int): List<CitySentence>
}