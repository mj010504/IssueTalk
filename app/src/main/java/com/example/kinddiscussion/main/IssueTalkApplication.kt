package com.example.kinddiscussion.main

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class IssueTalkApplication : Application()  {
    override fun onCreate() {
        super.onCreate()
    }
}