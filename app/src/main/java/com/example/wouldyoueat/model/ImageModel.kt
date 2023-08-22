package com.example.wouldyoueat.model

data class ImageModel(
    val photos: List<Photo>
)

data class Photo(
    val src: Image
)

data class Image(
    val original: String
)
