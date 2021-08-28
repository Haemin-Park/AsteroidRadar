package com.example.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.example.asteroidradar.data.PictureOfDay
import com.example.asteroidradar.database.getDatabase
import com.example.asteroidradar.network.NetworkService
import com.example.asteroidradar.repository.AsteroidsRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val asteroidsRepository = AsteroidsRepository(database)

    init {
        viewModelScope.launch {
            asteroidsRepository.refreshAsteroids()
        }
    }

    enum class AsteroidsFilter {
        WEEK, TODAY, ALL
    }

    private val asteroidsFilter = MutableLiveData(AsteroidsFilter.WEEK)
    fun updateFilter(filter: AsteroidsFilter) {
        asteroidsFilter.value = filter
    }

    val asteroids = Transformations.switchMap(asteroidsFilter) {
        when (it) {
            AsteroidsFilter.TODAY -> asteroidsRepository.todayAsteroids
            AsteroidsFilter.ALL -> asteroidsRepository.allAsteroids
            else -> asteroidsRepository.weekAsteroids
        }
    }

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    fun getTodayPicture() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = NetworkService.api1.getPictureOfDay()
            } catch (e: Exception) {
            }
        }
    }

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MainViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}