package com.engr429.watcher

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.engr429.watcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotificationDelegate {

    private lateinit var binding: ActivityMainBinding

    private val imageKey = "key"
    private val labelKey = "label"
    private val serverUrl = "https://watcher-images.s3.eu-central-1.amazonaws.com"

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
            val s3Key = data.getString(imageKey)
            val label = data.getString(labelKey)
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
        Glide.with(this).load("$serverUrl/$key").into(binding.imgScene)
    }

    private fun setupView() {
        with(binding) {
            viewBlocker.setOnClickListener {}
            groupToggle.addOnButtonCheckedListener { _, id, isChecked -> onButtonChecked(id, isChecked) }
            btnGetScene.setOnClickListener { getSceneFromCam() }
        }
    }

    private fun getSceneFromCam() {
        binding.btnGetScene.setOnClickListener {
            val bmp = (binding.imgScene.drawable as BitmapDrawable).bitmap
            binding.imgScene.setImageBitmap(bmp)
        }
    }

    private fun onButtonChecked(id: Int, isChecked: Boolean) {
        if (isChecked) {
            if (id == binding.btnAuto.id) {
                showAngleUi(false)
                // todo: notify cam to go auto
            } else {
                showAngleUi(true)
                // notify cam to go manual
            }
        }
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
        val s3Key = data[imageKey]
        val label = data[labelKey]
        handleNotificationData(s3Key, label)
    }

//    private fun getColor(@ColorRes colorId: Int): Color {
//
//    }
}