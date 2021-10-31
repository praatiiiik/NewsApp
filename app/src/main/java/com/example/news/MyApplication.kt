package com.example.news

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application() {

    // A essential step to Activate Dagger Hilt
}