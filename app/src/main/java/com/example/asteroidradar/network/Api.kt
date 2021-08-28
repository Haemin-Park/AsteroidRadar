package com.example.asteroidradar.network

import com.example.asteroidradar.Constants
import com.example.asteroidradar.data.Asteroid
import com.example.asteroidradar.data.PictureOfDay
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("/neo/rest/v1/feed")
    suspend fun getAsteroid(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): String

    @GET("/planetary/apod")
    suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): PictureOfDay
}