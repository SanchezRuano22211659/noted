package com.ozg.testappcomposekotlin

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun ExploradorDeArchivos (navController: NavController){
    var textoBusqueda by remember { mutableStateOf(TextFieldValue("")) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        // Contenido principal (buscador)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 70.dp) // espacio para no tapar el buscador con el footer
        ) {
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                placeholder = { Text("Buscar...") },
                leadingIcon = {
                    Image(
                        painter = painterResource(id = R.drawable.icon_search),
                        contentDescription = "Icono de búsqueda",
                        modifier = Modifier.padding(8.dp)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Escribiste: ${textoBusqueda.text}",
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        // Footer fijo
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF4A0072),
                            Color(0xFFBA68C8),
                            Color(0xFF7B1FA2)
                        )
                    )
                )
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { Log.d("NAV", "Navegando a crearNota")
                navController.navigate("ScreenCrearNota") }) {
                Image(
                    painter = painterResource(id = R.drawable.icon_file),
                    contentDescription = "Home"
                )
            }
            IconButton(onClick = { Log.d("NAV", "Navegando a crearCarpeta")
                navController.navigate("ScreenCrearCarpeta") }) {
                Image(
                    painter = painterResource(id = R.drawable.icon_folder),
                    contentDescription = "Buscar"
                )
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "explorador") {
        composable("explorador") { ExploradorDeArchivos(navController) }
        composable("ScreenCrearNota") { CrearNota() }
        composable("ScreenCrearCarpeta") { CrearCarpeta() }
    }
}

@Preview(showBackground = true)
@Composable
fun previews(){
    val fakeNavController = rememberNavController()
    ExploradorDeArchivos(navController = fakeNavController)
}