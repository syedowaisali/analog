package com.mumdyverse.analog.dsl

interface LogVerboseChainScope {
    fun v(message: String?)
    fun v(key: String, value: Any?)
}