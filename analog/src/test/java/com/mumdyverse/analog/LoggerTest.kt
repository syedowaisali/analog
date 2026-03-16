package com.mumdyverse.analog

import android.util.Log
import android.util.Log.isLoggable
import com.mumdyverse.analog.api.logChain
import com.mumdyverse.analog.api.logDebug
import com.mumdyverse.analog.api.logDebugChain
import com.mumdyverse.analog.api.logError
import com.mumdyverse.analog.api.logErrorChain
import com.mumdyverse.analog.api.logInfo
import com.mumdyverse.analog.api.logInfoChain
import com.mumdyverse.analog.api.logVerbose
import com.mumdyverse.analog.api.logVerboseChain
import com.mumdyverse.analog.api.logWarn
import com.mumdyverse.analog.api.logWarnChain
import com.mumdyverse.analog.api.logWtf
import com.mumdyverse.analog.api.logWtfChain
import com.mumdyverse.analog.core.LogManager
import com.mumdyverse.analog.dsl.LogChainScope
import com.mumdyverse.analog.integration.LogAdapter
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test

class LoggerTest {

    private lateinit var logAdapter: LogAdapter

    @Before
    fun setup() {
        logAdapter = mockk(relaxed = true) {
            every { isLoggable(any(), any(), any(), any()) } returns true
        }

        LogManager.init {
            adapter = logAdapter
        }
    }

    @Test
    fun logVerbose_givenTagAndMessage_expectedVerboseLogCall() {
        val tag = "TestTag"
        val message = "This is a verbose message"

        logVerbose(tag) { message }

        verify { logAdapter.log(Log.VERBOSE, tag, message, null) }
    }

    @Test
    fun logVerboseChain_givenTagAndBlock_expectedMultipleVerboseLogCalls() {

        val tag = "TestTag"

        logVerboseChain(tag) {
            v("Verbose message")
            v("key", "value")
        }

        verify {
            logAdapter.log(Log.VERBOSE, tag, "Verbose message", null)
            logAdapter.log(Log.VERBOSE, tag, "key = value", null)
        }
    }

    @Test
    fun logDebug_givenTagAndMessage_expectedDebugLogCall() {

        val tag = "TestTag"
        val message = "This is a debug message"

        logDebug(tag) { message }

        verify { logAdapter.log(Log.DEBUG, tag, message, null) }
    }

    @Test
    fun logDebugChain_givenTagAndBlock_expectedMultipleDebugLogCalls() {

        val tag = "TestTag"

        logDebugChain(tag) {
            d("Debug message")
            d("key", "value")
        }

        verify {
            logAdapter.log(Log.DEBUG, tag, "Debug message", null)
            logAdapter.log(Log.DEBUG, tag, "key = value", null)
        }
    }

    @Test
    fun logInfo_givenTagAndMessage_expectedInfoLogCall() {

        val tag = "TestTag"
        val message = "This is an info message"

        logInfo(tag) { message }

        verify { logAdapter.log(Log.INFO, tag, message, null) }
    }

    @Test
    fun logInfoChain_givenTagAndBlock_expectedMultipleInfoLogCalls() {

        val tag = "TestTag"

        logInfoChain(tag) {
            i("Info message")
            i("key", "value")
        }

        verify {
            logAdapter.log(Log.INFO, tag, "Info message", null)
            logAdapter.log(Log.INFO, tag, "key = value", null)
        }
    }

    @Test
    fun logWarn_givenTagAndMessage_expectedWarnLogCall() {

        val tag = "TestTag"
        val message = "This is a warning message"

        logWarn(tag) { message }

        verify { logAdapter.log(Log.WARN, tag, message, null) }
    }

    @Test
    fun logWarnChain_givenTagAndBlock_expectedMultipleWarnLogCalls() {

        val tag = "TestTag"

        logWarnChain(tag) {
            w("Warning message")
            w("key", "value")
        }

        verify {
            logAdapter.log(Log.WARN, tag, "Warning message", null)
            logAdapter.log(Log.WARN, tag, "key = value", null)
        }
    }

    @Test
    fun logError_givenTagAndMessage_expectedErrorLogCall() {

        val tag = "TestTag"
        val message = "This is an error message"

        logError(tag) { message }

        verify { logAdapter.log(Log.ERROR, tag, message, null) }
    }

    @Test
    fun logError_givenTagAndThrowableAndMessage_expectedErrorLogCallWithThrowable() {

        val tag = "TestTag"
        val throwable = Exception("An error occurred")
        val message = "This is an error message"

        logError(tag, throwable) { message }

        verify { logAdapter.log(Log.ERROR, tag, message, throwable) }
    }

    @Test
    fun logErrorChain_givenTagAndBlock_expectedMultipleErrorLogCalls() {

        val tag = "TestTag"
        val throwable = Exception("Error occurred")

        logErrorChain(tag) {
            e("Error message")
            e("key", "value")
            e(throwable, "Error with exception")
        }

        verify {
            logAdapter.log(Log.ERROR, tag, "Error message", null)
            logAdapter.log(Log.ERROR, tag, "key = value", null)
            logAdapter.log(Log.ERROR, tag, "Error with exception", throwable)
        }
    }

    @Test
    fun logWtf_givenTagAndMessage_expectedWtfLogCall() {

        val tag = "TestTag"
        val message = "What a Terrible Failure"

        logWtf(tag) { message }

        verify { logAdapter.log(Log.ASSERT, tag, message, null) }
    }

    @Test
    fun logWtfChain_givenTagAndBlock_expectedMultipleWtfLogCalls() {

        val tag = "TestTag"

        logWtfChain(tag) {
            wtf("WTF message")
            wtf("key", "value")
        }

        verify {
            logAdapter.log(Log.ASSERT, tag, "WTF message", null)
            logAdapter.log(Log.ASSERT, tag, "key = value", null)
        }
    }

    @Test
    fun logChain_givenTagAndMultipleLogLevels_expectedMultipleLogCalls() {

        val tag = "TestTag"

        logChain(tag) {
            v("Verbose message")
            d("Debug message")
            i("Info message")
            w("Warning message")
            e("Error message")
            wtf("WTF message")
        }

        verify {
            logAdapter.log(Log.VERBOSE, tag, "Verbose message", null)
            logAdapter.log(Log.DEBUG, tag, "Debug message", null)
            logAdapter.log(Log.INFO, tag, "Info message", null)
            logAdapter.log(Log.WARN, tag, "Warning message", null)
            logAdapter.log(Log.ERROR, tag, "Error message", null)
            logAdapter.log(Log.ASSERT, tag, "WTF message", null)
        }
    }

    @Test
    fun logChain_givenTagAndBlockWithThrowable_expectedErrorLogCallsWithThrowable() {

        val tag = "TestTag"
        val throwable = Exception("Some error")

        logChain(tag) {
            e(throwable, "Error with throwable")
            wtf(throwable, "WTF with throwable")
        }

        verify {
            logAdapter.log(Log.ERROR, tag, "Error with throwable", throwable)
            logAdapter.log(Log.ASSERT, tag, "WTF with throwable", throwable)
        }
    }

    @Test
    fun logChain_givenTagAndEmptyBlock_expectedNoLogCalls() {

        val tag = "TestTag"
        val block: LogChainScope.() -> Unit = {}

        logChain(tag, block)

        verify(exactly = 0) { logAdapter.log(any(), any(), any(), any()) }
    }
}