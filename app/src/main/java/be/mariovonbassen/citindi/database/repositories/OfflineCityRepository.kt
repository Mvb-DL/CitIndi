package be.mariovonbassen.citindi.database.repositories

import androidx.lifecycle.LiveData
import be.mariovonbassen.citindi.database.dao.CityDao
import be.mariovonbassen.citindi.database.dao.CitySentenceDao
import be.mariovonbassen.citindi.models.city.City
import be.mariovonbassen.citindi.models.city.CitySentence
import be.mariovonbassen.citindi.models.city.relations.CityWithSentences

class OfflineCityRepository(private val cityDao: CityDao,
                            private val citySentenceDao: CitySentenceDao) : CityRepository  {

    override suspend fun upsertCity(city: City) = cityDao.upsertCity(city)

    override suspend fun deleteCity(city: City) = cityDao.deleteCity(city)

    override suspend fun getCitiesByUserId(userId: Int): List<City>
        { return cityDao.getCitiesByUserId(userId) }

    override suspend fun getCityByCityId(cityId: Int): City {
        return cityDao.getCityByCityId(cityId)
    }
    override suspend fun getLatestCity(userId: Int): City {
        return cityDao.getLatestCity(userId)
    }
    override suspend fun insertCitySentence(citySentence: CitySentence) {
        cityDao.insertCitySentence(citySentence)
    }

    override suspend fun getCitySentencesForCity(cityId: Int): List<CitySentence> {
        return cityDao.getCitySentencesForCity(cityId)
    }

}