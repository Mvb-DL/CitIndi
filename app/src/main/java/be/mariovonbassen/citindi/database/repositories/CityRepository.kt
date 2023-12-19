package be.mariovonbassen.citindi.database.repositories


import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.city.CitySentence


interface CityRepository {
    suspend fun upsertCity(city: City)
    suspend fun deleteCity(city: City)
    suspend fun getCitiesByUserId(userId: Int): List<City>
    suspend fun getCityByCityId(cityId: Int): City
    suspend fun getLatestCity(userId: Int): City
    suspend fun insertCitySentence(citySentence: CitySentence)
    suspend fun getCitySentencesForCity(cityId: Int): List<CitySentence>
}