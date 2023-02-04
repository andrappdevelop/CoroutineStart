package com.example.coroutinestart

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.example.coroutinestart.databinding.ActivityMainBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.buttonLoad.setOnClickListener {
            binding.progressBar.isVisible = true
            binding.buttonLoad.isEnabled = false
            val jobCity = lifecycleScope.launch {
                val city = loadCity()
                binding.textViewLocation.text = city
            }
            val jobTemperature = lifecycleScope.launch {
                val temperature = loadTemperature()
                binding.textViewTemperature.text = temperature.toString()
            }
            lifecycleScope.launch {
                jobCity.join()
                jobTemperature.join()
                binding.progressBar.isVisible = false
                binding.buttonLoad.isEnabled = true
            }
        }
    }

    private suspend fun loadData() {
        binding.progressBar.isVisible = true
        binding.buttonLoad.isEnabled = false
        val city = loadCity()

        binding.textViewLocation.text = city
        val temperature = loadTemperature()

        binding.textViewTemperature.text = temperature.toString()
        binding.progressBar.isVisible = false
        binding.buttonLoad.isEnabled = true
    }

    private suspend fun loadCity(): String {
        delay(5000)
        return "Tomsk"
    }

    private suspend fun loadTemperature(): Int {
        delay(5000)
        return -17
    }

}