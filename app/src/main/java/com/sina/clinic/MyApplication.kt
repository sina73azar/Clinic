package com.sina.clinic

import android.app.Application
import net.time4j.android.ApplicationStarter

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ApplicationStarter.initialize(this, true);
    }
}