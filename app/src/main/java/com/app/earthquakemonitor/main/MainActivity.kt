package com.app.earthquakemonitor.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.earthquakemonitor.Earthquake
import com.app.earthquakemonitor.R
import com.app.earthquakemonitor.api.ApiResponseStatus
import com.app.earthquakemonitor.databinding.ActivityMainBinding

private const val SORT_TYPE_KEY = "sort_type"
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val sortType = getSortType()

        viewModel = ViewModelProvider(this, MainViewModelFactory(application, sortType)).get(MainViewModel::class.java)

        val adapter = EqAdapter()
        binding.eqRecycler.adapter = adapter

        viewModel.eqList.observe(this, Observer {
            eqList -> adapter.submitList(eqList)

            handleEmptyView(eqList, binding)
        })

        viewModel.status.observe(this, Observer {
            apiResponseStatus ->
                if(apiResponseStatus == ApiResponseStatus.LOADING){
                    binding.progressBar.visibility = View.VISIBLE
                } else if (apiResponseStatus == ApiResponseStatus.DONE){
                    binding.progressBar.visibility = View.GONE
                } else if (apiResponseStatus == ApiResponseStatus.ERROR){
                    binding.progressBar.visibility = View.GONE
                }
        })

        adapter.onItemClickListener = {
            Toast.makeText(this, it.place, Toast.LENGTH_SHORT).show()
        }
    }

    private fun getSortType(): Boolean {
        val prefs = getPreferences(MODE_PRIVATE)
        return prefs.getBoolean(SORT_TYPE_KEY, false)
    }

    private fun saveSortType(sortByMagnitude: Boolean){
        val prefs = getPreferences(MODE_PRIVATE)
        val editor = prefs.edit()
        editor.putBoolean(SORT_TYPE_KEY, sortByMagnitude)
        editor.apply()
    }

    private fun handleEmptyView(
        eqList: MutableList<Earthquake>,
        binding: ActivityMainBinding
    ) {
        if (eqList.isEmpty()) {
            binding.eqEmptyView.visibility = View.VISIBLE
        } else {
            binding.eqEmptyView.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.main_menu_sort_magnitude){
            viewModel.reloadEarthquakesFromDatabase(true)
            saveSortType(true)
        } else if(item.itemId == R.id.main_menu_sort_time){
            saveSortType(false)
            viewModel.reloadEarthquakesFromDatabase(false)
        }
        return super.onOptionsItemSelected(item)
    }
}