package com.example.kinddiscussion

import androidx.annotation.DrawableRes
import com.example.kinddiscussion.Screen.Search.route

sealed class Screen(
    val route : String,
    @DrawableRes val icon : Int
) {
    object Search :
            Screen(
                route = "search",
                icon = R.drawable.search
            )

    object Home :
            Screen(
                route = "home",
                icon = R.drawable.home
            )

    object Menu :
            Screen(
                route = "menu",
                icon = R.drawable.setting_ic
            )
}
