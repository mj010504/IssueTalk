package com.example.kinddiscussion.Firebase

import androidx.annotation.StringDef
import com.google.firebase.Timestamp
import org.w3c.dom.Comment

data class Subject(
    val title : String = "",
    val agreeText : String = "",
    val disagreeText : String = "",
    var agreeCount : Int = 0,
    var disagreeCount : Int = 0,
    var neutralCount : Int = 0,
    val userId : String = "",
    val postCount : Int = 0,
    val subjectField : String = "",
    val date : String = " ",
    val timestamp : Timestamp = Timestamp.now()

)


data class Post(
    val subjectId : String = "",
    val userId : String = "",
    val title : String = "",
    val field : String = "",
    val likeCount : Int = 0,
    val commentCount: Int = 0,
    val userName: String = "",
    val date : String = "",
    val content : String = "",
    val timestamp: Timestamp = Timestamp.now()
)


data class Comment(
    val userId: String = "",
    val postId : String = "",
    val userName : String = "",
    val date : String = "",
    val commentContent : String = ""
)

