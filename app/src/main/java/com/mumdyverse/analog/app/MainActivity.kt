package com.mumdyverse.analog.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mumdyverse.analog.api.logDebug
import com.mumdyverse.analog.api.logError
import com.mumdyverse.analog.api.logInfo
import com.mumdyverse.analog.api.logVerbose
import com.mumdyverse.analog.api.logWarn
import com.mumdyverse.analog.api.logWtf
import com.mumdyverse.analog.app.ui.theme.AnalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnalogTheme {
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    containerColor = Color.White
                ) { innerPadding ->
                    Analog(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Analog(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth().padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LogButton("Verbose") { logVerbose { "Verbose message" } }
            LogButton("Debug") { logDebug { "Debug message" } }
            LogButton("Info") { logInfo { "Info message" } }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            LogButton("Warn") { logWarn { "Warn message" } }
            LogButton("Error") { logError { "Error message" } }
            LogButton("Wtf") { logWtf { "Wtf message" } }
        }
    }
}

@Composable
fun LogButton(text: String, onClick: () -> Unit) {
    Button(onClick = onClick) {
        Text(text = text)
    }
}

@Preview(showBackground = true)
@Composable
fun AnalogPreview() {
    AnalogTheme {
        Analog()
    }
}