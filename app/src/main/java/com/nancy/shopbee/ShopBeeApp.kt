package com.nancy.shopbee

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ShopBeeApp : Application() {
    override fun onCreate() {
        super.onCreate()

        // Facebook SDK initialization
        com.facebook.FacebookSdk.sdkInitialize(this)
        com.facebook.appevents.AppEventsLogger.activateApp(this)
    }
}
