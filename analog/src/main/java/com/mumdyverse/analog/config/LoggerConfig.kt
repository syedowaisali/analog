package com.mumdyverse.analog.config

import com.mumdyverse.analog.integration.LogAdapter

internal data class LoggerConfig(
    val adapter: LogAdapter?,
    val defaultTag: String,
    val integrations: List<LogAdapter>
)