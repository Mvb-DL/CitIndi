package be.mariovonbassen.citindi.database.repositories

import be.mariovonbassen.citindi.database.dao.CityDao
import be.mariovonbassen.citindi.models.city.City

class OfflineCityRepository(private val cityDao: CityDao) : CityRepository  {

    override suspend fun upsertCity(city: City) = cityDao.upsertCity(city)

    override suspend fun deleteCity(city: City) = cityDao.deleteCity(city)

    override suspend fun getCitiesByUserId(userId: Int): List<City>
        { return cityDao.getCitiesByUserId(userId) }
}