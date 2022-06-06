package com.engr429.watcher

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
import com.bumptech.glide.Glide
import com.engr429.watcher.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupView()
    }

    private fun setupView() {
        with(binding) {
            viewBlocker.setOnClickListener {}
            groupToggle.addOnButtonCheckedListener { _, id, isChecked -> onButtonChecked(id, isChecked) }
            btnGetScene.setOnClickListener { getSceneFromCam() }
        }
    }

    private fun getSceneFromCam() {
        Glide.with(this).load("https://watcher-images.s3.eu-central-1.amazonaws.com/16ed484a911eef521f1de25df1844cbe").into(binding.imgScene)
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

//    private fun getColor(@ColorRes colorId: Int): Color {
//
//    }
}