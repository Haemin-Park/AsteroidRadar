package com.example.asteroidradar.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.asteroidradar.data.Asteroid
import com.example.asteroidradar.data.asDatabaseModel
import com.example.asteroidradar.database.AsteroidDatabase
import com.example.asteroidradar.database.asDomainModel
import com.example.asteroidradar.network.NetworkService
import com.example.asteroidradar.util.END_DATE
import com.example.asteroidradar.util.START_DATE
import com.example.asteroidradar.util.parseAsteroidsJsonResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidsRepository(private val database: AsteroidDatabase) {
    val weekAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getWeekAsteroids(START_DATE, END_DATE)) {
            it.asDomainModel()
        }
    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getTodayAsteroid(START_DATE)) {
            it.asDomainModel()
        }
    val allAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(database.asteroidDao.getAllAsteroid()) {
            it.asDomainModel()
        }

    suspend fun refreshAsteroids() {
        withContext(Dispatchers.IO) {
            val result = NetworkService.api2.getAsteroid(START_DATE, END_DATE)
            val dbAsteroids = parseAsteroidsJsonResult(JSONObject(result)).asDatabaseModel()
            database.asteroidDao.insertAll(*dbAsteroids)
        }
    }

    suspend fun deletePreAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deletePreAsteroids(START_DATE)
        }
    }

    suspend fun getTodayAsteroid() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.getTodayAsteroid(START_DATE)
        }
    }
}