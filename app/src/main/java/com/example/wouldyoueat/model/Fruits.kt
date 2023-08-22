package com.example.wouldyoueat.model

data class Fruits(
    val name: String,
    val id: Int,
    val nutritions: Nutrition
)

data class Nutrition(
    val calories: Int,
    val fat: Double,
    val sugar: Double,
    val carbohydrates: Double,
    val protein: Double
)
