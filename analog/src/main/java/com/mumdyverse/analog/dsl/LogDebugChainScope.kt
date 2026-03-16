package com.mumdyverse.analog.dsl

interface LogDebugChainScope {
    fun d(message: String?)
    fun d(key: String, value: Any?)
}