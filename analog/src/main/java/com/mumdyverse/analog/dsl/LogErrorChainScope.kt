package com.mumdyverse.analog.dsl

interface LogErrorChainScope {
    fun e(message: String?)
    fun e(key: String, value: Any?)
    fun e(t: Throwable, message: String? = null)
}