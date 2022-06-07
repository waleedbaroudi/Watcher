package com.engr429.watcher.main

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bumptech.glide.Glide
import com.engr429.watcher.all_scenes.AllScenesActivity
import com.engr429.watcher.R
import com.engr429.watcher.Constants.IMAGE_KEY
import com.engr429.watcher.Constants.LABEL_KEY
import com.engr429.watcher.Constants.S3_URL
import com.engr429.watcher.api.CallConsumer
import com.engr429.watcher.api.WatcherApi
import com.engr429.watcher.api.WatcherUpdate
import com.engr429.watcher.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity(), NotificationDelegate {

    private lateinit var binding: ActivityMainBinding
    private val api = WatcherApi.instance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        NotificationHandler.setDelegate(this)
        checkIntent()
        setupView()
    }

    private fun checkIntent() {
        intent.extras?.let { data ->
            val s3Key = data.getString(IMAGE_KEY)
            val label = data.getString(LABEL_KEY)
            handleNotificationData(s3Key, label)
        }
    }

    private fun handleNotificationData(s3Key: String?, label: String?) {
        runOnUiThread {
            Toast.makeText(this, "Found a $label", Toast.LENGTH_SHORT).show()
            loadImageFromKey(s3Key)
        }
    }

    private fun loadImageFromKey(key: String?) {
        if (key == null) {
            Toast.makeText(this, "NULL KEY", Toast.LENGTH_SHORT).show()
            return
        }
        Glide.with(this).load("$S3_URL/$key").into(binding.imgScene)
    }

    private fun setupView() {
        with(binding) {
            viewBlocker.setOnClickListener {}
            groupToggle.addOnButtonCheckedListener { _, id, isChecked -> onButtonChecked(id, isChecked) }
            btnGetScene.setOnClickListener { getSceneFromCam() }
            btnAllScenes.setOnClickListener { startActivity(Intent(this@MainActivity, AllScenesActivity::class.java)) }
        }
    }

    private fun getSceneFromCam() {
        val degree = binding.viewAngle.progress * 10
        val update = WatcherUpdate(2, degree)
        sendUpdate(update)
    }

    private fun onButtonChecked(id: Int, isChecked: Boolean) {
        if (isChecked) {
            val update = if (id == binding.btnAuto.id) {
                showAngleUi(false)
                WatcherUpdate(0)
            } else {
                showAngleUi(true)
                WatcherUpdate(1)
            }
            sendUpdate(update)
        }
    }

    private fun sendUpdate(update: WatcherUpdate) {
        CallConsumer.consume(api.sendUpdate(update), {}, { Log.e(TAG, "sendUpdate: ", it) })
    }


    private fun showAngleUi(show: Boolean) {
        binding.viewAngle.apply {
            indicatorColor = if (show) getColor(R.color.dark_blue) else getColor(R.color.transparent)
            progressPrimaryColor = if (show) getColor(R.color.blue) else getColor(R.color.gray)
            progressSecondaryColor = if (show) getColor(R.color.blue) else getColor(R.color.gray)
        }
        binding.viewBlocker.visibility = if (show) View.GONE else View.VISIBLE
        binding.btnGetScene.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun handleNotification(data: Map<String, String>) {
        val s3Key = data[IMAGE_KEY]
        val label = data[LABEL_KEY]
        handleNotificationData(s3Key, label)
    }
}