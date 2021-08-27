package com.example.asteroidradar.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidradar.Constants
import com.example.asteroidradar.data.PictureOfDay
import com.example.asteroidradar.network.NetworkService
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    fun getTodayPicture() {
        viewModelScope.launch {
            try {
                _pictureOfDay.value = NetworkService.api.getPictureOfDay(Constants.API_KEY)
                Log.d("network result", _pictureOfDay.value!!.url)
            } catch (e: Exception) {

            }
        }
    }
}