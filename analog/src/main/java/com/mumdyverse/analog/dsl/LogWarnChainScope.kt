package com.mumdyverse.analog.dsl

interface LogWarnChainScope {
    fun w(message: String?)
    fun w(key: String, value: Any?)
}