package com.ozg.noted

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview(showBackground = true)
@Composable
fun Previeww(){
    VerGrafo()
}

@Composable
fun VerGrafo() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(14.dp)
            .background(Color.White)
            .border(
                width = 1.dp,
                color = Color.Black,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(14.dp)
    ) {
        Text(
            text="Grafo",
            fontSize = 40.sp
        )
        // Aqui llamas la funcion para el grafo o colocas aqui directo el back del grafo
    }
}
