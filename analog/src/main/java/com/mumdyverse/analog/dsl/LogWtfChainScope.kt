package com.mumdyverse.analog.dsl

interface LogWtfChainScope {
    fun wtf(message: String?)
    fun wtf(key: String, value: Any?)
    fun wtf(t: Throwable, message: String? = null)
}