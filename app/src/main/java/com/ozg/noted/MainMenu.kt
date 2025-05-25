package com.ozg.noted

//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
//noinspection UsingMaterialAndMaterial3Libraries
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
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

val UnageoSemiBoltItaliz = FontFamily(Font(R.font.unageosmiboltitalic))

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainMenu() {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val optionsScreens =
            listOf(
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
            composable(OptionsScreens.ScreenCrearCarpeta.path) {
                val context = LocalContext.current
                CrearCarpeta(navController, context)
            }
            composable(OptionsScreens.ScreenCrearNota.path) {
                val context = LocalContext.current
                CrearNota(navController, context)
            }
            composable(OptionsScreens.ScreenGraphView.path) { VerGrafo(navController) }
            composable(OptionsScreens.ScreenExploradorArchivos.path) {
                ExploradorDeArchivos(navController)
            }
            composable(
                    route = "editor/{fileName}",
                    arguments = listOf(navArgument("fileName") { type = NavType.StringType })
            ) { backStackEntry ->
                val fileName = backStackEntry.arguments?.getString("fileName") ?: ""
                EditorScreen(fileName = fileName, navController = navController)
            }
        }
    }
}

@Composable
fun TopBar(scope: CoroutineScope, scaffoldState: ScaffoldState) {
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
                IconButton(onClick = { scope.launch { scaffoldState.drawerState.open() } }) {
                    Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Icon menu",
                            tint = Color.White
                    )
                }
            },
            backgroundColor = Color.Transparent,
            modifier =
                    Modifier.background(
                            brush =
                                    Brush.verticalGradient(
                                            colors =
                                                    listOf(
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
        options_list: List<OptionsScreens>,
        navController: NavHostController,
        closeDrawer: () -> Unit
) {
    Column(
            modifier =
                    Modifier.background(
                            brush =
                                    Brush.verticalGradient(
                                            colors =
                                                    listOf(
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
                modifier = Modifier.height(160.dp).fillMaxWidth(),
                contentScale = ContentScale.FillWidth
        )
        options_list.forEach { item ->
            DrawerItem(item = item) {
                navController.navigate(item.path) {
                    launchSingleTop = true
                    // popUpTo(0)
                }
                closeDrawer()
            }
        }
    }
}

@Composable
fun DrawerItem(item: OptionsScreens, onClick: () -> Unit) {
    Row(
            modifier =
                    Modifier.fillMaxWidth()
                            .clickable { onClick() }
                            .padding(horizontal = 20.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painterResource(id = item.icon), contentDescription = item.title)
        Spacer(modifier = Modifier.width(30.dp))
        Text(
                text = item.title,
                fontFamily = UnageoSemiBoltItaliz,
                fontSize = 27.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previews() {
    MainMenu()
}
