package com.mumdyverse.analog.integration

import android.util.Log
import java.io.PrintWriter
import java.io.StringWriter

/**
 * A concrete implementation of [LogAdapter] that outputs logs to the standard Android [Log] system.
 *
 * This adapter is typically used during development to redirect library logs to Logcat.
 * It handles message formatting and stack trace extraction for exceptions.
 */
open class DebugAdapter : LogAdapter {

    /**
     * Dispatches the log entry to the Android [Log] system.
     *
     * If the priority is [Log.ASSERT], it uses [Log.wtf]. Otherwise, it uses [Log.println].
     *
     * @param priority The log level (e.g., [Log.DEBUG], [Log.ERROR]).
     * @param tag The identifier for the log source.
     * @param message The message to be logged.
     * @param throwable An optional exception to include in the log output.
     */
    override fun log(
        priority: Int,
        tag: String,
        message: String?,
        throwable: Throwable?
    ) {
        val finalMessage = prepareMessage(message, throwable)
        if (priority == Log.ASSERT) {
            Log.wtf(tag, finalMessage)
        } else {
            Log.println(priority, tag, finalMessage)
        }
    }

    /**
     * Formats the final log string.
     *
     * Ensures that null or empty messages are replaced with an "" and
     * appends the stack trace if a [Throwable] is provided.
     *
     * @param message The raw message string.
     * @param t An optional exception.
     * @return A formatted string containing the message and/or stack trace.
     */
    private fun prepareMessage(message: String?, t: Throwable?): String {
        val baseMessage = message ?: ""
        return if (t != null) "$baseMessage\n${getStackTraceString(t)}" else baseMessage
    }

    /**
     * Converts a [Throwable] stack trace into a readable string.
     *
     * @param t The throwable to process.
     * @return The full stack trace as a string.
     */
    private fun getStackTraceString(t: Throwable): String {
        val sw = StringWriter(256)
        val pw = PrintWriter(sw, false)
        t.printStackTrace(pw)
        pw.flush()
        return sw.toString()
    }
}