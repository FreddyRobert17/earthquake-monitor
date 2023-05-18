package com.app.earthquakemonitor.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.app.earthquakemonitor.Earthquake
import com.app.earthquakemonitor.api.ApiResponseStatus
import com.app.earthquakemonitor.database.getDatabase
import kotlinx.coroutines.*
import java.net.UnknownHostException

class MainViewModel(private val application: Application, private val sortType: Boolean): AndroidViewModel(application) {
    private val database = getDatabase(application)
    private val repository = MainRepository(database)

    private val _status = MutableLiveData<ApiResponseStatus>()
    val status: LiveData<ApiResponseStatus>
            get() = _status

    private var _eqList = MutableLiveData<MutableList<Earthquake>>()
    val eqList: LiveData<MutableList<Earthquake>>
            get() = _eqList

    init {
        reloadEarthquakes()
    }

    private fun reloadEarthquakes() {
        viewModelScope.launch {
            try {
                _status.value = ApiResponseStatus.LOADING
                _eqList.value = repository.fetchEarthquakes(sortType)
                _status.value = ApiResponseStatus.DONE
            } catch (e: UnknownHostException) {
                _status.value = ApiResponseStatus.ERROR
                Log.d("TAG", "No internet connection", e)
            }
        }
    }

    fun reloadEarthquakesFromDatabase(sortByMagnitude: Boolean) {
        viewModelScope.launch {
                _eqList.value = repository.fetchEarthquakesFromDatabase(sortByMagnitude)
        }
    }
}