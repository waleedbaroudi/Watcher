package com.engr429.watcher.all_scenes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.engr429.watcher.api.CallConsumer
import com.engr429.watcher.api.WatcherApi
import com.engr429.watcher.databinding.ActivityAllScenesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "AllScenesActivity"

class AllScenesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllScenesBinding
    private lateinit var api: WatcherApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScenesBinding.inflate(layoutInflater)
        api = WatcherApi.create()
        setContentView(binding.root)
        getSceneKeys()
    }

    private fun getSceneKeys() {
        CallConsumer.consume(api.getSceneKeys(),
            onSuccess = { handleSceneKeys(it) },
            onFailure = { Log.e(TAG, "onFailure: ERROR", it) })
    }

    private fun handleSceneKeys(keys: List<String>) {
        binding.recyclerScenes.apply {
            adapter = SceneAdapter(keys)
            layoutManager = GridLayoutManager(this@AllScenesActivity, 2)
        }
        binding.tvAllScenes.animate().translationY(0f).setInterpolator(DecelerateInterpolator()).withEndAction {
            runOnUiThread {
                binding.recyclerScenes.visibility = View.VISIBLE
            }
        }
    }
}