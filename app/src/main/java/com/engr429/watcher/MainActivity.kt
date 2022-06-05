package com.engr429.watcher

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.ColorRes
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