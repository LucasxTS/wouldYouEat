package com.example.wouldyoueat

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.view.animation.AccelerateInterpolator
import androidx.appcompat.app.AppCompatActivity
import com.example.wouldyoueat.GlideModule.GlideModule
import com.example.wouldyoueat.apiRequest.EndPoint
import com.example.wouldyoueat.apiRequest.FruitsImageInterface
import com.example.wouldyoueat.apiRequest.RetrofitBuilder
import com.example.wouldyoueat.databinding.ActivityMainBinding
import com.example.wouldyoueat.model.Fruits
import com.example.wouldyoueat.model.ImageModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Random

class MainActivity() : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var fruits: List<Fruits> = listOf()
    private var currentFruitId: Int = 0
    private val fruitsEatenList = mutableListOf<Pair<String, Boolean>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        callingFunctions()
    }
    private fun callingFunctions() {
        textDegradeColor()
        getFruitsData()
        buttonEatClick()
        buttonNotEatClick()
    }
    private fun moveCardView(direction: Float) {
        binding.cardView.animate()
            .translationXBy(direction)
            .rotation(direction / 10)
            .setInterpolator(AccelerateInterpolator())
            .setDuration(500)
            .withEndAction {
                currentFruitId++
                bindingDescription(fruits)
                loadImage(fruits)
            }
            .start()
    }
    private fun buttonEatClick() {
        binding.eat.setOnClickListener {
            val fruitName = fruits[currentFruitId].name
            fruitsEatenList.add(fruitName to true)
            moveCardView(1000f)
        }
    }
    private fun buttonNotEatClick() {
        binding.notEat.setOnClickListener {
            val fruitName = fruits[currentFruitId].name
            fruitsEatenList.add(fruitName to false)
            moveCardView(-1000f)
        }
    }
    private fun textDegradeColor() {
        val appTitle = binding.appTitle
        val appTitleText = "Fruits"
        val colors = intArrayOf(
            resources.getColor(R.color.yellow),
            resources.getColor(R.color.orange),
            resources.getColor(R.color.red)
        )
        val shader = LinearGradient(
            0f, 0f, 0f, appTitle.textSize,
            colors,
            null,
            Shader.TileMode.CLAMP
        )
        appTitle.text = appTitleText
        appTitle.paint.shader = shader
    }
    private fun getFruitsData() {
        val gson = Gson()
        val retrofitClient = RetrofitBuilder.getRetrofitInstance("https://www.fruityvice.com/")
        val endPoint = retrofitClient.create(EndPoint::class.java)
        val callback = endPoint.getFruits()

        callback.enqueue(object : Callback<List<Fruits>> {
            override fun onResponse(call: Call<List<Fruits>>, response: Response<List<Fruits>>) {
                val jsonFruits = response.body()
                if (jsonFruits != null) {
                    jsonFruits
                    fruits = jsonFruits.shuffled(random = Random())
                    loadImage(fruits)
                    bindingDescription(fruits)
                }
            }

            override fun onFailure(call: Call<List<Fruits>>, t: Throwable) {
                println(t)
            }
        })
    }
    private fun bindingDescription(fruits: List<Fruits>) {
        binding.fruitName.text = fruits[currentFruitId].name
        binding.prothein.text = "${fruits[currentFruitId].nutritions.protein}"
        binding.fat.text = "${fruits[currentFruitId].nutritions.fat}"
        binding.carbohydrate.text = "${fruits[currentFruitId].nutritions.carbohydrates}"

    }
    private fun loadImage(fruitName: List<Fruits>) {
        val retrofitInstance = RetrofitBuilder.getRetrofitInstance("https://api.pexels.com/v1/")
        val endPoint = retrofitInstance.create(FruitsImageInterface::class.java)
        val fruitNameToQuery = fruitName[currentFruitId].name
        val call = endPoint.getFruitImage(
            "TUqYKU35kjF3a8li1HLEswYjBQqQNfR8NgPd2EmgdnvqsWsRpswpXTZA",
            fruitNameToQuery
        )

        call.enqueue(object : Callback<ImageModel> {
            override fun onResponse(call: Call<ImageModel>, response: Response<ImageModel>) {
                if (response.isSuccessful) {
                    val image = response.body()
                    if (image != null) {
                        val imageToImageVIew = image.photos[0].src.original
                        val setImage = GlideModule.setImage(binding.fruitPhoto, this@MainActivity, imageToImageVIew)
                        binding.cardView.animate().translationX(0f)
                        binding.cardView.animate().rotation(0f)
                    }
                }
            }
            override fun onFailure(call: Call<ImageModel>, t: Throwable) {
                println(t)
            }
        })
    }
}