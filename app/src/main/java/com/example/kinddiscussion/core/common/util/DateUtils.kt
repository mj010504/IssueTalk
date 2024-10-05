package com.example.kinddiscussion.core.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun getCurrentDateFormatted(): String {
    // 현재 날짜를 가져옴
    val currentDate = LocalDateTime.now()

    // 원하는 형식의 DateTimeFormatter 생성
    val formatter = DateTimeFormatter.ofPattern("yy.MM.dd HH:mm")

    // 날짜를 포맷하여 문자열로 반환
    return currentDate.format(formatter)
}
