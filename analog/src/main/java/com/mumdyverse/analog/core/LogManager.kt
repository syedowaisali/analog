package com.mumdyverse.analog.core

import com.mumdyverse.analog.config.LoggerConfig
import com.mumdyverse.analog.core.LogManager.config
import com.mumdyverse.analog.core.LogManager.defaultTag
import com.mumdyverse.analog.integration.LogAdapter

object LogManager {

    /**
     * The current active configuration for the logger.
     */
    private var config: LoggerConfig = LoggerConfigBuilder().build()

    /**
     * The base [LogAdapter] used to log messages.
     *
     * to determine if a log should be processed or to modify the log metadata before
     * it reaches the native Log implementation. It is retrieved from the current [config].
     */
    val adapter: LogAdapter?
        get() = config.adapter

    /**
     * The fallback tag used for logs when no specific tag is provided.
     * Derived from the current [config].
     */
    val defaultTag: String
        get() = config.defaultTag

    /**
     * A list of registered [LogAdapter] integrations.
     * These integrations allow logs to be forwarded to external services.
     */
    val integrations: List<LogAdapter>
        get() = config.integrations


    /**
     * Initializes the [LogManager] with a custom configuration.
     *
     * This function uses a DSL-style builder to set up logging parameters such as
     * [LogAdapter]s, the primary implementation, and default tags.
     * Calling this method replaces the existing configuration.
     *
     * Example usage:
     * ```
     * LogManager.init {
     *     defaultTag = "MyApp"
     *     loggable = MyCustomLoggable()
     *     addIntegration(SentryAdapter())
     * }
     * ```
     *
     * @param block A lambda with [LoggerConfigBuilder] as the receiver for configuring the logger.
     */
    fun init(block: LoggerConfigBuilder.() -> Unit) {
        val builder = LoggerConfigBuilder().apply(block)
        config = builder.build()
    }

    /**
     * Determines the appropriate log tag for a given log entry.
     *
     * If an [explicitTag] is provided, it is used immediately. Otherwise, this method performs
     * a stack trace analysis to automatically identify the caller's class name.
     *
     * The stack trace analysis filters out:
     * - Timber internal classes (`timber.log.*`).
     * - This library's internal logging infrastructure (`com.mumdyverse.analog.*`).
     * - Jetpack Compose anonymous classes containing the string "composable".
     *
     * @param explicitTag An optional tag provided by the user.
     * @return The [explicitTag], the name of the calling class, or [defaultTag] if no valid
     * caller can be identified.
     */
    internal fun resolveTag(explicitTag: String?): String {
        if (explicitTag != null) return explicitTag

        val stackTrace = Throwable().stackTrace
        val caller = stackTrace.firstOrNull {
            !it.className.startsWith("timber.log.") &&
                    !it.className.startsWith("com.mumdyverse.analog") &&
                    it.className != LogManager::class.java.name
        }

        return caller
            ?.className
            ?.takeIf { !it.contains("composable", true) }
            ?.substringAfterLast('.')
            ?: defaultTag
    }
}