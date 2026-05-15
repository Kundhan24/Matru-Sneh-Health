package com.matrusneh.health

import android.app.Application
import com.matrusneh.health.util.NotificationHelper

class MatruSnehApp : Application() {
    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createChannels(this)
    }
}
