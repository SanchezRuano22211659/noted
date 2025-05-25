package com.ozg.noted

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EntryAnimation()
        }
    }
}

@Composable
fun EntryAnimation() {
    var showLoading by remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        delay(3000)
        showLoading = false
    }

    if (showLoading) {
        LoadingScreen()
    } else {
        MainMenu()
    }
}
