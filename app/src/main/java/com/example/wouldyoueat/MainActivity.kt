package com.example.wouldyoueat

import android.graphics.LinearGradient
import android.graphics.Shader
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.wouldyoueat.apiRequest.EndPoint
import com.example.wouldyoueat.apiRequest.RetrofitBuilder
import com.example.wouldyoueat.databinding.ActivityMainBinding
import com.example.wouldyoueat.model.Fruits
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var fruits : List<Fruits>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callingFunctions()

    }


    private fun callingFunctions() {
        textDegradeColor()
        getData()
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

    private fun getData() {
        val retrofitClient = RetrofitBuilder.getRetrofitInstance("https://www.fruityvice.com/")
        val endPoint = retrofitClient.create(EndPoint::class.java)
        val callback = endPoint.getFruits()

        callback.enqueue(object : Callback<List<Fruits>> {
            override fun onResponse(call: Call<List<Fruits>>, response: Response<List<Fruits>>) {
                
            }

            override fun onFailure(call: Call<List<Fruits>>, t: Throwable) {
                println(t)
            }

        })
    }
}