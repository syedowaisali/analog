package com.mumdyverse.analog.integration

interface LogAdapter {

    /**
     * Determines whether a specific log entry should be processed by this integration.
     *
     * this provides a granular filter for the adapter.
     * It allows the implementation to decide if a log should be forwarded to its
     * specific external service based on the log's [priority], [tag], or content.
     *
     * @param priority The log level (e.g., [android.util.Log.DEBUG], [android.util.Log.ERROR]).
     * @param tag The identifier for the log source.
     * @param message The message being logged.
     * @param throwable An optional exception associated with the log.
     * @return `true` if the adapter should handle this log, `false` otherwise.
     * Defaults to `true`.
     */
    fun isLoggable(
        priority: Int,
        tag: String,
        message: Any?,
        throwable: Throwable?
    ): Boolean = true

    /**
     * Executes the logging operation for this integration.
     *
     * This method is called by the logging system to deliver a log event to a specific
     * external integration (e.g., Crashlytics, Sentry, or a file logger). Implementations
     * should handle the actual transmission or storage of the log data here.
     *
     * Note: This method is typically called only if [isLoggable] returns `true`.
     *
     * @param priority The log level (e.g., [android.util.Log.INFO], [android.util.Log.WARN]).
     * @param tag The identifier for the log source.
     * @param message The message to be logged.
     * @param throwable An optional exception associated with the log event.
     */
    fun log(
        priority: Int,
        tag: String,
        message: Any?,
        throwable: Throwable?
    )
}