package com.ozg.noted

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.fontResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CrearNota() {
    var mostrarEditor by remember { mutableStateOf(false) }
    var tituloNota by remember { mutableStateOf("") }
    var contenidoNota by remember { mutableStateOf("") }
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
            )
            .clickable { mostrarEditor = true } // Mostrar campos al tocar
            .padding(16.dp)
    ) {
        if (mostrarEditor) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 55.dp), // espacio para el botón
                verticalArrangement = Arrangement.Top
            ) {
                // Título de la nota
                OutlinedTextField(
                    value = tituloNota,
                    onValueChange = { tituloNota = it },
                    placeholder = { Text("Título de la nota", fontFamily = UnageoSemiBoltItaliz,color = Color.White, fontSize = 18.sp)},
                    textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp)
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp)),
                    singleLine = true
                )

                // Contenido de la nota
                BasicTextField(
                    value = contenidoNota,
                    onValueChange = { contenidoNota = it },
                    textStyle = TextStyle(
                        fontSize = 14.sp,
                        color = Color.White,
                        fontFamily = UnageoSemiBoltItaliz,
                        ),
                    cursorBrush = SolidColor(Color.White),
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .background(Color.Transparent)
                        .padding(10.dp)
                        .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp)),
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier
                                .background(Color.Transparent)
                                .padding(12.dp) //padding interno
                        ) {
                            innerTextField()
                        }
                    }
                )
                Spacer(modifier = Modifier.height(18.dp))
            }

            // Botón de guardar nota abajo centrado
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(10.dp)
                    .background(Color.Transparent)
            ) {
                Button(
                    onClick = {
                        GuardarNota() //funcion que hace el back para guardar la nota
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = Color.White,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                    ),
                ) {
                    Text("Guardar nota", color = Color.White, fontSize = 30.sp, fontFamily = UnageoSemiBoltItaliz,)
                }
            }
        } else {
            Text(
                text = "Toca para comenzar una nueva nota",
                fontFamily = UnageoSemiBoltItaliz,
                fontSize = 20.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}

fun GuardarNota(){
    //funcion que hace el back para guardar la nota
}
@Preview(showBackground = true)
@Composable
fun previewss(){
    CrearNota()
}
