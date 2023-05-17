package com.app.earthquakemonitor.api

class Geometry(val coordinates: Array<Double>) {
    val longitude: Double
        get() = coordinates[0]

    val latitude: Double
        get() = coordinates[1]
}