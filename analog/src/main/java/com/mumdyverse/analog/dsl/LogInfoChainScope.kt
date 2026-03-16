package com.mumdyverse.analog.dsl

interface LogInfoChainScope {
    fun i(message: String?)
    fun i(key: String, value: Any?)
}