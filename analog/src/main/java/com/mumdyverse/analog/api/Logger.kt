package com.mumdyverse.analog.api

import android.util.Log
import com.mumdyverse.analog.core.LogInfo
import com.mumdyverse.analog.core.LogManager
import com.mumdyverse.analog.dsl.BaseLogScope
import com.mumdyverse.analog.dsl.LogChainScope
import com.mumdyverse.analog.dsl.LogDebugChainScope
import com.mumdyverse.analog.dsl.LogErrorChainScope
import com.mumdyverse.analog.dsl.LogInfoChainScope
import com.mumdyverse.analog.dsl.LogVerboseChainScope
import com.mumdyverse.analog.dsl.LogWarnChainScope
import com.mumdyverse.analog.dsl.LogWtfChainScope
import com.mumdyverse.analog.integration.LogAdapter

/**
 * Logs a [Log.VERBOSE] level message.
 *
 * @param tag The identifier for the log source. If null, a tag will be automatically resolved.
 * @param message A lambda returning the string to be logged. The lambda is only evaluated if logging is enabled.
 */
fun logVerbose(tag: String? = null, message: () -> String) = withTag(tag) { tag ->
    log(Log.VERBOSE, tag, message(), null)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.VERBOSE] level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple verbose log entries.
 */
fun logVerboseChain(tag: String? = null, block: LogVerboseChainScope.() -> Unit) =
    withTag(tag) { tag ->
        logChain(tag, block)
    }

/**
 * Logs a [Log.DEBUG] level message.
 *
 * @param tag The identifier for the log source.
 * @param message A lambda returning the string to be logged.
 */
fun logDebug(tag: String? = null, message: () -> Any?) = withTag(tag) { tag ->
    log(Log.DEBUG, tag, message(), null)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.DEBUG] level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple debug log entries.
 */
fun logDebugChain(tag: String? = null, block: LogDebugChainScope.() -> Unit) = withTag(tag) { tag ->
    logChain(tag, block)
}

/**
 * Logs a [Log.INFO] level message.
 *
 * @param tag The identifier for the log source.
 * @param message A lambda returning the string to be logged.
 */
fun logInfo(tag: String? = null, message: () -> Any?) = withTag(tag) { tag ->
    log(Log.INFO, tag, message(), null)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.INFO] level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple info log entries.
 */
fun logInfoChain(tag: String? = null, block: LogInfoChainScope.() -> Unit) = withTag(tag) { tag ->
    logChain(tag, block)
}

/**
 * Logs a [Log.WARN] level message.
 *
 * @param tag The identifier for the log source.
 * @param message A lambda returning the string to be logged.
 */
fun logWarn(tag: String? = null, message: () -> Any?) = withTag(tag) { tag ->
    log(Log.WARN, tag, message(), null)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.WARN] level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple warning log entries.
 */
fun logWarnChain(tag: String? = null, block: LogWarnChainScope.() -> Unit) = withTag(tag) { tag ->
    logChain(tag, block)
}

/**
 * Logs a [Log.ERROR] level message.
 *
 * @param tag The identifier for the log source.
 * @param message A lambda returning the string to be logged.
 */
fun logError(tag: String? = null, message: () -> Any?) = withTag(tag) { tag ->
    log(Log.ERROR, tag, message(), null)
}

/**
 * Logs a [Log.ERROR] level message along with a [Throwable].
 *
 * @param t The exception or error to log.
 * @param message A lambda returning the string context for the error.
 */
fun logError(t: Throwable?, message: (() -> Any?)? = null) {
    logError(null, t, message)
}

/**
 * Logs a [Log.ERROR] level message along with a [Throwable].
 *
 * @param tag The identifier for the log source.
 * @param t The exception or error to log.
 * @param message A lambda returning the string context for the error.
 */
fun logError(tag: String? = null, t: Throwable?, message: (() -> Any?)? = null) = withTag(tag) { tag ->
    log(Log.ERROR, tag, message?.invoke(), t)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.ERROR] level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple error log entries.
 */
fun logErrorChain(tag: String? = null, block: LogErrorChainScope.() -> Unit) = withTag(tag) { tag ->
    logChain(tag, block)
}

/**
 * Logs a "What a Terrible Failure" ([Log.ASSERT]) level message.
 *
 * @param tag The identifier for the log source.
 * @param message A lambda returning the string to be logged.
 */
fun logWtf(tag: String? = null, message: () -> Any?) = withTag(tag) { tag ->
    log(Log.ASSERT, tag, message(), null)
}

/**
 * Initiates a DSL-based logging chain specifically for [Log.ASSERT] (WTF) level entries.
 *
 * @param tag The identifier for the log source.
 * @param block DSL scope for defining multiple WTF log entries.
 */
fun logWtfChain(tag: String? = null, block: LogWtfChainScope.() -> Unit) = withTag(tag) { tag ->
    logChain(tag, block)
}

/**
 * Core function for executing a chain of log statements within a defined scope.
 *
 * It aggregates multiple log entries defined in the [block] and dispatches them
 * sequentially to the configured log integrations.
 *
 * @param tag The identifier for the log source.
 * @param block The DSL scope containing various log definitions.
 */
fun logChain(tag: String? = null, block: LogChainScope.() -> Unit) =
    withTag(tag) { tag ->
        val scope = BaseLogScope().apply(block)
        scope.build().forEach { info ->
            when (info) {
                is LogInfo.Message -> log(info.priority, tag, info.message, null)
                is LogInfo.Error -> log(info.priority, tag, info.message, info.throwable)
            }
        }
    }

/**
 * Helper to ensure a valid tag is used. If [tag] is null, it resolves a default tag
 * via [LogManager.resolveTag].
 */
private inline fun withTag(tag: String?, block: (String) -> Unit) {
    block(LogManager.resolveTag(tag))
}

/**
 * Internal dispatch function that routes log data to the primary adapter and
 * any additional registered integrations.
 */
private fun log(priority: Int, tag: String, message: Any?, t: Throwable?) {
    runIntegrations(
        adapters = listOf(LogManager.adapter) + LogManager.integrations,
        priority = priority,
        tag = tag,
        message = message,
        t = t
    )
}

/**
 * Iterates through a list of [LogAdapter]s and dispatches the log event to each.
 *
 * This function performs a safety check on the provided adapters, filters them based on
 * their individual [LogAdapter.isLoggable] criteria, and then executes the logging
 * operation for those that pass the filter.
 *
 * @param adapters A list of potential [LogAdapter] integrations to process.
 * @param priority The log level (e.g., [Log.DEBUG], [Log.ERROR]).
 * @param tag The identifier for the log source.
 * @param message The message being logged.
 * @param t An optional [Throwable] associated with the log entry.
 */
private fun runIntegrations(
    adapters: List<LogAdapter?>,
    priority: Int,
    tag: String,
    message: Any?,
    t: Throwable?,
) {
    adapters
        .mapNotNull { it }
        .filter { it.isLoggable(priority, tag, message, t) }
        .forEach { adapter ->
            adapter.log(priority, tag, message, t)
        }
}