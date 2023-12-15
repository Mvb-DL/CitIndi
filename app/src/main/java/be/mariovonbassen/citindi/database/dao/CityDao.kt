package be.mariovonbassen.citindi.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City
@Dao
interface CityDao {

    @Upsert
    suspend fun upsertCity(city: City)

    @Delete
    suspend fun deleteCity(city: City)

    @Query("SELECT * FROM cities WHERE userId = :userId")
    fun getCitiesByUserId(userId: Int): List<City>
}