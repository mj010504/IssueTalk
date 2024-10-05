package com.example.kinddiscussion.core.network.model

import com.google.firebase.Timestamp

data class Subject(
    val title : String = "",
    val agreeText : String = "",
    val disagreeText : String = "",
    var agreeCount : Int = 0,
    var disagreeCount : Int = 0,
    var neutralCount : Int = 0,
    val userId : String = "",
    var postCount : Int = 0,
    val subjectField : String = "",
    val date : String = " ",
    val timestamp : Timestamp = Timestamp.now()

)
