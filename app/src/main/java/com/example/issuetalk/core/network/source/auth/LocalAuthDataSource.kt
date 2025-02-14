package com.example.issuetalk.core.network.source.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalAuthDataSource @Inject constructor(
    @ApplicationContext private val context : Context
) {
    fun saveShowHome() {
        val sharedPreferences = context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("showHome", true)
        editor.apply()
    }

     fun getShowHome(): Boolean {
        val sharedPreferences = context.getSharedPreferences("auth_pref", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("showHome", false)
    }
}