package com.example.pbl_sns_25

data class PostDTO(
    var text: String? = null,
    var imageUrl: String? = null,
    var uid: String? = null,
    var userId: String? = null,
    //var timestamp: Long? = null,
    var favoriteCount: Int = 0
)
