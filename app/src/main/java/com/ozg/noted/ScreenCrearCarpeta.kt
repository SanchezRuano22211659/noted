package com.ozg.noted

//noinspection UsingMaterialAndMaterial3Libraries,UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Text
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ozg.noted.core.FileManager


@Preview(showBackground = true)
@Composable
fun previewsss(){
    val mockNavController = rememberNavController()
    val context = LocalContext.current
    CrearCarpeta(mockNavController, context)
}

@Composable
fun CrearCarpeta(navController : NavController, context: Context){
    var nombreCarpeta by remember { mutableStateOf("") }

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

            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 55.dp),
            verticalArrangement = Arrangement.Top

        ) {
            // Nombre de la carpeta
            androidx.compose.material3.OutlinedTextField(
                value = nombreCarpeta,
                onValueChange = { nombreCarpeta = it },
                placeholder = {
                    Text(
                        "Nombre de la carpeta",
                        fontFamily = UnageoSemiBoltItaliz,
                        color = Color.White,
                        fontSize = 18.sp
                    )
                },
                textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp)),
                singleLine = true
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .background(Color.Transparent)
                    .padding(10.dp)
                    .border(width = 1.dp, color = Color.White, shape = RoundedCornerShape(4.dp)),
                contentAlignment = Alignment.Center
            ){
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ){
                Text(
                    text = "Recuerda que en NOTED debes crear tus notas dentro de la carpeta donde quieres que se guarden",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontFamily = UnageoSemiBoltItaliz,
                    textAlign = TextAlign.Center
                )
                Image(
                    painter = painterResource(id = R.drawable.gracias),
                    contentDescription = "Gracias",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(16.dp)
                )
                }
            }
            Spacer(modifier = Modifier.height(18.dp))
        }

        // Botón de Crear carpeta
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(10.dp)
                .background(Color.Transparent)
        ) {
            Button(
                onClick = {
                    GuardarCarpeta(nombreCarpeta, navController, context)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.White,
                        shape = RoundedCornerShape(4.dp)
                    ),
                colors = buttonColors(
                    Color.Transparent
                ),
            ) {
                Text(
                    "Crear Carpeta",
                    color = Color.White,
                    fontSize = 30.sp,
                    fontFamily = UnageoSemiBoltItaliz
                )
            }
        }
    }
    
}

    fun GuardarCarpeta(folderName: String,navController: NavController, context: Context) {
    if (folderName.isNotEmpty()) {
        val success = FileManager.createFolder(folderName)
        if (success) {
            navController.popBackStack()
        }
    }
}
