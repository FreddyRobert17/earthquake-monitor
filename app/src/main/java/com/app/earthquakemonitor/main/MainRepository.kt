package com.app.earthquakemonitor.main

import com.app.earthquakemonitor.Earthquake
import com.app.earthquakemonitor.api.EqJsonResponse
import com.app.earthquakemonitor.api.service
import com.app.earthquakemonitor.database.EqDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainRepository(private val database: EqDatabase) {
    suspend fun fetchEarthquakes(): MutableList<Earthquake> {
        return withContext(Dispatchers.IO){
            val eqJsonResponse = service.getLastHourEarthquakes()
            val eqList = parseEqResult(eqJsonResponse)
            eqList
        }
    }

    private fun parseEqResult(eqJsonResponse: EqJsonResponse): MutableList<Earthquake> {
        val eqList = mutableListOf<Earthquake>()
        val featureList = eqJsonResponse.features

        for(feature in featureList){
            val id = feature.id
            val properties = feature.properties

            val magnitude = properties.mag
            val place = properties.place
            val time = properties.time

            val geometry = feature.geometry

            val longitude = geometry.longitude
            val latitude = geometry.latitude

            eqList.add(Earthquake(id, place, magnitude, time, latitude, longitude))
        }
        return eqList
    }
}