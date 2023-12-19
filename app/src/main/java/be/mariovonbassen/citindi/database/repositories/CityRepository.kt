package be.mariovonbassen.citindi.database.repositories

import androidx.lifecycle.LiveData
import be.mariovonbassen.citindi.models.User
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.city.CitySentence
import be.mariovonbassen.citindi.models.city.relations.CityWithSentences

interface CityRepository {
    suspend fun upsertCity(city: City)
    suspend fun deleteCity(city: City)
    suspend fun getCitiesByUserId(userId: Int): List<City>
    suspend fun getCityByCityId(cityId: Int): City
    suspend fun getLatestCity(userId: Int): City
    suspend fun insertCitySentence(citySentence: CitySentence)
    suspend fun getCitySentencesForCity(cityId: Int): List<CitySentence>
}