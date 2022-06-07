package com.engr429.watcher

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.engr429.watcher.databinding.ActivityAllScenesBinding

class AllScenesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllScenesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScenesBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}