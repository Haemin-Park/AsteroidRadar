package com.example.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface AsteroidDao {
    @Query("select * from databaseasteroid where closeApproachDate>=:today and closeApproachDate<:endDay order by closeApproachDate asc")
    fun getWeekAsteroids(today: String, endDay: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseasteroid where closeApproachDate==:today")
    fun getTodayAsteroid(today: String): LiveData<List<DatabaseAsteroid>>

    @Query("select * from databaseasteroid order by closeApproachDate asc")
    fun getAllAsteroid(): LiveData<List<DatabaseAsteroid>>

    @Query("delete from databaseasteroid where closeApproachDate <:today")
    fun deletePreAsteroids(today: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroids: DatabaseAsteroid)
}

@Database(entities = [DatabaseAsteroid::class], version = 1)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                AsteroidDatabase::class.java, "asteroids"
            ).build()
        }
    }
    return INSTANCE
}