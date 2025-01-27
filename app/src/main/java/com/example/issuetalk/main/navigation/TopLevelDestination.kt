package com.example.issuetalk.main.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

enum class TopLevelDestination(
    val route : String,
    @DrawableRes val selectedIcon : Int,
    @DrawableRes val unselectedIcon : Int,
    @StringRes val iconTextId: Int,
    @StringRes val titleTextId: Int,
    ) {
//        Home(
//            route = homeRoute,
//            selectedIcon = Icons.Default.Home,
//            unselectedIcon = Icons.Default.Home,
//            iconTextId = R.string.home,
//            titleTextId = R.string.home
//        )
}



