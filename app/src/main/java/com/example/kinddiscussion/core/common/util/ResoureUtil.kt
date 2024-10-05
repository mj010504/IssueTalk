package com.example.kinddiscussion.core.common.util

import com.example.kinddiscussion.R

fun fieldToImage(field : String) : Int{

    when(field) {
        "사회" -> return R.drawable.society
        "정치" -> return R.drawable.politics
        "연예" -> return R.drawable.entertainments
        "경제" -> return R.drawable.economy
        "기타" -> return R.drawable.etc
    }

    return R.drawable.society

}
