package com.example.wouldyoueat

import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wouldyoueat.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callingFunctions()
    }


    private fun callingFunctions() {
     textDegradeColor()
    }
    private fun textDegradeColor() {
        val appTitle = binding.appTitle
        val appTitleText = "Fruits"
        val colors = intArrayOf(resources.getColor(R.color.yellow),resources.getColor(R.color.orange), resources.getColor(R.color.red))
        val shader = LinearGradient(
            0f, 0f, 0f, appTitle.textSize,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        appTitle.text = appTitleText
        appTitle.paint.shader = shader
    }

    private fun requestFruits() {

    }
}