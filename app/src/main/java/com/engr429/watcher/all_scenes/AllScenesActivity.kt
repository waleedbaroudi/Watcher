package com.engr429.watcher.all_scenes

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.recyclerview.widget.GridLayoutManager
import com.engr429.watcher.api.CallConsumer
import com.engr429.watcher.api.WatcherApi
import com.engr429.watcher.databinding.ActivityAllScenesBinding

private const val TAG = "AllScenesActivity"

class AllScenesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAllScenesBinding
    private val api = WatcherApi.instance
    private val productImagePreview = ImagePreviewDialog()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAllScenesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getSceneKeys()
    }

    private fun getSceneKeys() {
        CallConsumer.consume(api.getSceneKeys(),
            onSuccess = { handleSceneKeys(it) },
            onFailure = { Log.e(TAG, "onFailure: ERROR", it) })
    }

    private fun onSceneClicked(scene: Drawable) {
        productImagePreview.show(supportFragmentManager, scene)
    }

    private fun handleSceneKeys(keys: List<String>) {
        binding.recyclerScenes.apply {
            adapter = SceneAdapter(keys.reversed()) { scene -> onSceneClicked(scene) }
            layoutManager = GridLayoutManager(this@AllScenesActivity, 2)
        }
        binding.tvAllScenes.animate().translationY(0f).setInterpolator(DecelerateInterpolator()).withEndAction {
            runOnUiThread {
                binding.recyclerScenes.visibility = View.VISIBLE
            }
        }
    }
}