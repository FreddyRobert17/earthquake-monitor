package com.app.earthquakemonitor

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.earthquakemonitor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.eqRecycler.layoutManager = LinearLayoutManager(this)

        val eqList = mutableListOf<Earthquake>()
        eqList.add(Earthquake("1", "57 Km East NY", 4.3, 273846L, -102.4756, 28.3477))
        eqList.add(Earthquake("2", "57 Km East NY", 4.3, 273846L, -102.4756, 28.3477))
        eqList.add(Earthquake("3", "57 Km East NY", 4.3, 273846L, -102.4756, 28.3477))
        eqList.add(Earthquake("4", "57 Km East NY", 4.3, 273846L, -102.4756, 28.3477))


        val adapter = EqAdapter()
        binding.eqRecycler.adapter = adapter
        adapter.submitList(eqList)
    }
}