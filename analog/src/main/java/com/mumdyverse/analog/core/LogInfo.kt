package com.mumdyverse.analog.core

internal sealed interface LogInfo {
    data class Message(val priority: Int, val message: String?): LogInfo
    data class Error(val priority: Int, val throwable: Throwable, val message: String?): LogInfo
}