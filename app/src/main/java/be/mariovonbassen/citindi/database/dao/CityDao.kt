package be.mariovonbassen.citindi.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import be.mariovonbassen.citindi.models.city.City

interface CityDao {

    @Insert
    suspend fun insertCity(city: City)


    /*@Query("SELECT * FROM City WHERE id = :id")
    suspend fun getBooksByAuthorId(id: int): List<City>*/
}