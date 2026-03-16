package com.mumdyverse.analog.app

import android.app.Application
import com.mumdyverse.analog.core.LogManager
import com.mumdyverse.analog.integration.DebugAdapter

class AnalogApp : Application() {
    override fun onCreate() {
        super.onCreate()
        LogManager.init {
            adapter = DebugAdapter()
            defaultTag = "AnalogApp"
        }
    }
}