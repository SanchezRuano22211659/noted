package com.ozg.noted

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

val AirstrikeFont = FontFamily(
    Font(R.font.airstrike)
)
@Composable
fun LoadingScreen() {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4A0072),
                        Color(0xFFBA68C8),
                        Color(0xFF7B1FA2)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "NOTED",
            fontSize = 90.sp,
            fontFamily = AirstrikeFont,
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview(){
    LoadingScreen()
}
