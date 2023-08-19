package com.example.wouldyoueat.model

data class Fruits(
    val name : String,
    val id : Int,
    val nutrition : ArrayList<Nutrition>
)

data class Nutrition(
    val calories : Int,
    val fat : Double,
    val sugar : Double,
    val carbohydrates : Double
)
