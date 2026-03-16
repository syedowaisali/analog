# Analog

**Analog** is a flexible, reusable, and extendable logging system for Android applications. It simplifies logging, reduces boilerplate, and provides a standardized approach to logging with support for both single and chained log entries.

## Overview

This library introduces a centralized logging system, making it easier to manage log levels, log messages, and integrations across your entire application. Whether for debugging or production environments, **Analog** allows you to streamline the logging process with minimal effort.

### Key Features:
- **Independent & Reusable Logger**: Fully independent and reusable across multiple modules without modification.
- **Centralized Logging System**: All logging routes through a single entry point for consistent behavior.
- **Flexible Log Methods**: Simple and chain-based methods for logging individual or grouped messages.
- **Extensible via LogAdapter**: Easily integrate third-party logging services or custom loggers.
- **Configurable via LogManager**: Customize log behavior and adapters during app initialization.


## Installation

You can install **Analog** in your project via Gradle. To add the module to your project, include the following dependency in your `build.gradle`:

```gradle
dependencies {
    implementation 'com.mumdyverse:analog:1.0.0'
}
```

## Configuration

### LogManager Initialization

The **LogManager** class is used to configure the logger system. A primary adapter is required for logging to work. If no adapter is provided, logging will not be functional.

You can provide your own custom adapter or use the default DebugAdapter(), which outputs logs using Android's Log.

Initialize it on Application class.

### How to Initialize:

```kotlin
LogManager.init {
    // Required: Provide a primary log adapter for internal logging (e.g., DebugAdapter or a custom adapter)
    adapter = DebugAdapter() // Or provide your custom LogAdapter
    
    // Optional: Add external integrations (e.g., Crashlytics, Sentry, etc.)
    integrations = listOf(CrashlyticsAdapter()) // External service integrations
    
    // Optional: Set the default log tag, `Logger` tag will used if not provided
    defaultTag = "MyAppTag"
}
```

### Parameters for Initialization:

**adapter** (Required): The primary log adapter used for internal logging. If not specified, logging will not work. You can use the provided `DebugAdapter()` or implement your own custom adapter that extends `LogAdapter`.

**integrations** (Optional): A list of external log adapters used for additional logging services like Crashlytics or Sentry. These adapters are only used for integrations and do not affect the core log behavior.

**defaultTag** (Optional): The fallback tag used for logs when no specific tag is provided. The default is `"Logger"`.


## Logging Methods
### Single Log Methods

These methods allow you to log messages at different levels (VERBOSE, DEBUG, INFO, WARN, ERROR, ASSERT):

- `logVerbose {}`
- `logDebug {}`
- `logInfo {}`
- `logWarn {}`
- `logError {}`
- `logWtf {}`

```kotlin
logVerbose("MainActivity") { "This is a verbose log message" }
logDebug { "This is a debug log message" }
logInfo("NetworkModule") { "This is an info log message" }
logWarn { "This is a warning log message" }
logError { "This is an error log message" }
logWtf { "This is a WTF log message" }

```

### Chain Log Methods

For logging sequences or workflows, use the chain-based log methods. These methods allow you to log multiple messages in a chain without calling individual log methods repeatedly:

- `logChain {}`
- `logVerboseChain {}`
- `logDebugChain {}`
- `logInfoChain {}`
- `logWarnChain {}`
- `logErrorChain {}`
- `logWtfChain {}`

```kotlin
logVerboseChain(tag = "UserActions") {
    v("User clicked button")
    v("Navigating to next screen")
}

logErrorChain {
    e("Network request failed")
    e(t = throwable, message = { "Timeout error while fetching data" })
}
```

### Optional Tagging

You can optionally provide a custom tag for any log method.

- If you provide a `tag` parameter, it will be used directly.
- If no `tag` is provided, the logger will attempt to resolve it using the class name of the calling method.
- If the class name cannot be resolved, the default tag specified in `LogManager` will be used.

```kotlin
logVerbose(tag = "CustomTag") { "This is a custom tag log message" }

logDebug { "This log will automatically use the calling class name as tag" }
```

### Custom Log Adapters

You can extend `LogAdapter` to create custom logging outputs. For example, to log to a remote service or a file:

```kotlin
class CustomLogAdapter : LogAdapter {
    override fun isLoggable(priority: Int, tag: String, message: String?, throwable: Throwable?): Boolean {
        return true // Define custom log filtering logic
    }

    override fun log(priority: Int, tag: String, message: String?, throwable: Throwable?) {
        // Implement custom logging behavior (e.g., send logs to a remote server)
    }
}

```

## Advanced Features

### Chain Logging

The `logChain` method allows you to group multiple log statements together in a clean and concise manner. This is particularly useful for logging workflows or sequences of actions.

```kotlin
logDebugChain {
    v("Verbose message")
    d("Debug message")
    i("Info message")
    w("Warning message")
    e("Error message")
    wtf("WTF message")
}
```

### External Integrations

You can integrate with third-party services by implementing the `LogAdapter` interface and adding your custom logic.

For example, to log messages to Sentry

```kotlin
class SentryLogAdapter : LogAdapter {
    override fun isLoggable(priority: Int, tag: String, message: String?, throwable: Throwable?): Boolean {
        return priority >= Log.ERROR // Only log errors or higher
    }

    override fun log(priority: Int, tag: String, message: String?, throwable: Throwable?) {
        // Send the log to Sentry
        Sentry.captureMessage(message)
    }
}
```

### Contributing

We welcome contributions to improve `Analog`. Feel free to open issues or submit pull requests with enhancements, bug fixes, or documentation improvements.

### Conclusion

**Analog** is a powerful and flexible logging system for Android that can be easily extended and integrated with various logging backends. With its clean API, it helps developers maintain consistent logging practices while supporting a wide range of logging requirements, from simple debug logs to more complex multi-chain workflows.

### License
This project is licensed under the **MIT License**.
```
MIT License

Copyright (c) 2026 Syed Owais Ali

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

```