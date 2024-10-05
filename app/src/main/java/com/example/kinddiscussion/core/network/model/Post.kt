package com.example.kinddiscussion.core.network.model

import com.google.firebase.Timestamp

data class Post(
    val subjectId : String = "",
    val userId : String = "",
    var title : String = "",
    val field : String = "",
    var likeCount : Int = 0,
    var commentCount: Int = 0,
    val userName: String = "",
    val date : String = "",
    var content : String = "",
    val timestamp: Timestamp = Timestamp.now()
)


data class Comment(
    val userId: String = "",
    val postId : String = "",
    val userName : String = "",
    val date : String = "",
    val commentContent : String = "",
    val timestamp: Timestamp = Timestamp.now()
)
