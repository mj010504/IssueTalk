package com.example.issuetalk.core.network.source.auth

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class LocalAuthDataSource @Inject constructor(
    @ApplicationContext private val context: Context
) {
    fun saveShowHome() {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_SHOW_HOME, true)
        editor.apply()
    }

    fun getShowHome(): Boolean {
        val sharedPreferences = context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean(KEY_SHOW_HOME, false)
    }

    fun clearShowHome() {
        context.getSharedPreferences(AUTH_PREF, Context.MODE_PRIVATE)
            .edit()
            .remove(KEY_SHOW_HOME)
            .apply()
    }


    companion object {
        private const val AUTH_PREF = "auth_pref"
        private const val KEY_SHOW_HOME = "showHome"
    }
}