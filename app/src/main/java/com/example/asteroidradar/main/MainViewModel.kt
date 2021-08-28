package com.example.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.api.END_DATE
import com.example.asteroidradar.api.START_DATE
import com.example.asteroidradar.api.parseAsteroidsJsonResult
import com.example.asteroidradar.data.Asteroid
import com.example.asteroidradar.data.PictureOfDay
import com.example.asteroidradar.network.NetworkService
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _asteroid = MutableLiveData<List<Asteroid>>()
    val asteroid: LiveData<List<Asteroid>>
        get() = _asteroid

    fun getTodayPicture() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = NetworkService.api1.getPictureOfDay()
                Log.d("network result", _pictureOfDay.value.toString())
            } catch (e: Exception) {
                Log.d("network error", e.toString())
            }
        }
    }

    fun getPeriodAsteroid() {
        viewModelScope.launch {
            try {
                val result = NetworkService.api2.getAsteroid(START_DATE, END_DATE)
                _asteroid.value = parseAsteroidsJsonResult(JSONObject(result))
                Log.d("network result", _asteroid.value.toString())
            } catch (e: Exception) {
                Log.d("network error", e.toString())
            }
        }
    }
}