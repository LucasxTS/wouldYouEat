package com.example.wouldyoueat.model

data class Fruits(
    val name : String,
    val id : Int,
    val nutrition : ArrayList<nutritions>
)

data class nutritions(
    val calories : Int,
    val fat : Double,
    val sugar : Double,
    val carbohydrates : Double
)
