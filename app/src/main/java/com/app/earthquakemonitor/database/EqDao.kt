package com.app.earthquakemonitor.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.app.earthquakemonitor.Earthquake

@Dao
interface EqDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(eqList: MutableList<Earthquake>)

    @Query("SELECT * FROM earthquakes")
    fun getEarthquakes(): MutableList<Earthquake>

    @Query("SELECT * FROM earthquakes ORDER BY magnitude DESC")
    fun getEarthquakesByMagnitude(): MutableList<Earthquake>
}