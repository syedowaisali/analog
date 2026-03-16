package com.mumdyverse.analog.core

import com.mumdyverse.analog.config.LoggerConfig
import com.mumdyverse.analog.integration.LogAdapter

class LoggerConfigBuilder {

    /**
     * A base [LogAdapter] used to logs.
     *
     * Unlike the [integrations] list which handles multiple downstream services,
     * this adapter typically represents a single base handler for the logging process.
     */
    var adapter: LogAdapter? = null

    /**
     * The fallback tag used for log entries when no specific tag is provided.
     * Defaults to "Logger".
     */
    var defaultTag: String = "Logger"

    /**
     * A list of [LogAdapter] implementations to be registered as integrations.
     * These allow logs to be forwarded to external services (e.g., Crashlytics or custom backends).
     */
    var integrations = listOf<LogAdapter>()

    /**
     * Creates a [LoggerConfig] instance from the properties set in this builder.
     *
     * This internal method consolidates the configured [adapter], [defaultTag], and [integrations]
     * into an immutable [LoggerConfig] object, which is then used to initialize the logging system.
     *
     * @return A new [LoggerConfig] instance with the specified configuration.
     */
    internal fun build(): LoggerConfig = LoggerConfig(
        adapter = adapter,
        defaultTag = defaultTag,
        integrations = integrations
    )
}