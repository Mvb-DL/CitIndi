package be.mariovonbassen.citindi.database.repositories

import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City

interface CityRepository {
    suspend fun upsertCity(city: City)
    suspend fun deleteCity(city: City)
    suspend fun getCitiesByUserId(userId: Int): List<City>
}