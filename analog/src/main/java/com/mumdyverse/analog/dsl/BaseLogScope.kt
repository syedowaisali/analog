package com.mumdyverse.analog.dsl

import android.util.Log
import com.mumdyverse.analog.core.LogInfo
import com.mumdyverse.analog.utils.LogFormatter

internal class BaseLogScope : LogChainScope {
    private val lines = mutableListOf<LogInfo>()

    override fun v(message: String?) {
        log(Log.VERBOSE, message)
    }

    override fun v(key: String, value: Any?) {
        log(Log.VERBOSE, key, value)
    }

    override fun d(message: String?) {
        log(Log.DEBUG, message)
    }

    override fun d(key: String, value: Any?) {
        log(Log.DEBUG, key, value)
    }

    override fun i(message: String?) {
        log(Log.INFO, message)
    }

    override fun i(key: String, value: Any?) {
        log(Log.INFO, key, value)
    }

    override fun w(message: String?) {
        log(Log.WARN, message)
    }

    override fun w(key: String, value: Any?) {
        log(Log.WARN, key, value)
    }

    override fun e(message: String?) {
        log(Log.ERROR, message)
    }

    override fun e(key: String, value: Any?) {
        log(Log.ERROR, key, value)
    }

    override fun e(t: Throwable, message: String?) {
        log(Log.ERROR, t, message)
    }

    override fun wtf(message: String?) {
        log(Log.ASSERT, message)
    }

    override fun wtf(key: String, value: Any?) {
        log(Log.ASSERT, key, value)
    }

    override fun wtf(t: Throwable, message: String?) {
        log(Log.ASSERT, t, message)
    }

    private fun log(priority: Int, message: String?) {
        lines += LogInfo.Message(priority, message)
    }

    private fun log(priority: Int, key: String, value: Any?) {
        lines += LogInfo.Message(priority, LogFormatter.formatKeyValue(key, value))
    }

    private fun log(priority: Int, throwable: Throwable, message: String?) {
        lines += LogInfo.Error(priority, throwable, message)
    }

    fun build(): List<LogInfo> = lines
}