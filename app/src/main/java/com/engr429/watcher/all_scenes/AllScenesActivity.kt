package com.engr429.watcher.all_scenes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.engr429.watcher.databinding.ActivityAllScenesBinding

class AllScenesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAllScenesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScenesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSceneKeys()
    }

    private fun getSceneKeys() {


    }

    private fun handleSceneKeys(keys: List<String>) {
        binding.recyclerScenes.apply {
            adapter = SceneAdapter(keys)
            layoutManager = GridLayoutManager(this@AllScenesActivity, 2)
        }
    }
}