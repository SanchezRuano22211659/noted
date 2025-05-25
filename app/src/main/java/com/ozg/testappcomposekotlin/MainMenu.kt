package com.ozg.testappcomposekotlin

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.IconButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ScaffoldState
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


val UnageoSemiBoltItaliz = FontFamily(
    Font(R.font.unageosmiboltitalic)
)

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainMenu(){
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val optionsScreens = listOf(
        OptionsScreens.ScreenCrearCarpeta,
        OptionsScreens.ScreenCrearNota,
        OptionsScreens.ScreenGraphView,
        OptionsScreens.ScreenExploradorArchivos
    )
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar(scope, scaffoldState) },
        drawerContent = {
            Drawer(
                options_list = optionsScreens,
                navController = navController,
                closeDrawer = { scope.launch { scaffoldState.drawerState.close() } }
            )
        }
    ) {
        NavHost(
            navController = navController,
            startDestination = OptionsScreens.ScreenExploradorArchivos.path
        ) {
            composable(OptionsScreens.ScreenCrearCarpeta.path) { CrearCarpeta() }
            composable(OptionsScreens.ScreenCrearNota.path) { CrearNota() }
            composable(OptionsScreens.ScreenGraphView.path) { VerGrafo() }
            composable(OptionsScreens.ScreenExploradorArchivos.path) { ExploradorDeArchivos(navController) }
        }
    }
}

@Composable
fun TopBar(
    scope: CoroutineScope,
    scaffoldState: ScaffoldState
) {
    TopAppBar(
        title = {
            Text(
                text = "NOTED",
                fontFamily = AirstrikeFont,
                fontSize = 45.sp,
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = {
                scope.launch {
                    scaffoldState.drawerState.open()
                }
            }) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = "Icon menu",
                    tint = Color.White
                )
            }
        },
        backgroundColor = Color.Transparent,
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF4A0072),
                    Color(0xFFBA68C8),
                    Color(0xFF7B1FA2)
                )
            )
        )
    )
}

@Composable
fun Drawer(
    options_list : List<OptionsScreens>,
    navController: NavHostController,
    closeDrawer: () -> Unit
){
    Column (
        modifier = Modifier.background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFF3B005C),
                    Color(0xFFA050B2),
                    Color(0xFF691A8A)
                )
            )
        )
    ) {
        Image(
            painterResource(id = R.drawable.imgmenulateral),
            contentDescription = "Menu de opciones",
            modifier = Modifier
                .height(160.dp)
                .fillMaxWidth(),
            contentScale = ContentScale.FillWidth
        )
        options_list.forEach{item ->
            DrawerItem(item = item) {
                navController.navigate(item.path) {
                    launchSingleTop = true
                    //popUpTo(0)
                }
                closeDrawer()
            }
        }
    }
}

@Composable
fun DrawerItem(item: OptionsScreens, onClick: () -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 20.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Image(painterResource(id = item.icon),
            contentDescription = item.title)
        Spacer(modifier = Modifier.width(30.dp))
        Text(text = item.title,
            fontFamily = UnageoSemiBoltItaliz,
            fontSize = 27.sp,
            color = Color.White,
            modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun Previews(){
    MainMenu()
}