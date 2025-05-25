package com.ozg.noted

import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ozg.noted.core.NoteDatabaseHelper
import java.io.File
import kotlin.math.cos
import kotlin.math.sin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun VerGrafo(navController: NavController? = null, refreshCounter: Int = 0) {
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    var lastTap by remember { mutableLongStateOf(0L) }

    val context = LocalContext.current
    val dbHelper = remember { NoteDatabaseHelper(context) }

    var refreshCounter by remember { mutableStateOf(0) }

    var nodes by remember { mutableStateOf<List<String>>(emptyList()) }
    var links by remember { mutableStateOf<List<Pair<String, String>>>(emptyList()) }

    LaunchedEffect(refreshCounter) {
        withContext(Dispatchers.IO) {
            nodes = dbHelper.getAllNotes()
            links = dbHelper.getAllLinks()
        }
    }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            nodes = dbHelper.getAllNotes()
            links = dbHelper.getAllLinks()
        }
    }

    val nodePositions =
            remember(nodes) {
                if (nodes.isNotEmpty()) {
                    calculateNodePositions(nodes.size, context)
                } else emptyList()
            }

    Box(
            modifier =
                    Modifier.fillMaxSize()
                            .pointerInput(Unit) {
                                detectTransformGestures(
                                        onGesture = { _, pan, zoom, _ ->
                                            scale = maxOf(0.5f, minOf(scale * zoom, 3f))
                                            offset += pan
                                        }
                                )
                            }
                            .pointerInput(Unit) {
                                detectTapGestures(
                                        onDoubleTap = { tapOffset: Offset ->
                                            val now = System.currentTimeMillis()
                                            if (now - lastTap < 300L) {
                                                nodePositions.forEachIndexed { index, position ->
                                                    val screenPosition = position * scale + offset
                                                    val transformedPos = position * scale + offset
                                                    val distance =
                                                            (tapOffset - transformedPos)
                                                                    .getDistance()

                                                    if (distance < 80f) {
                                                        navController?.navigate(
                                                                "editor/${File(nodes[index]).name}"
                                                        ) {
                                                            launchSingleTop = true
                                                            popUpTo("graphView") {
                                                                saveState = true
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                            lastTap = now
                                        }
                                )
                            }
                            .padding(14.dp)
                            .background(Color.White)
                            .border(
                                    width = 1.dp,
                                    color = Color.Black,
                                    shape = RoundedCornerShape(8.dp)
                            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val textStyle = TextStyle(color = Color.Black, fontSize = 14.sp)

            translate(offset.x, offset.y) {
                scale(scale) {
                    // Dibujar conexiones
                    links.forEach { (source, target) ->
                        val sourceIndex = nodes.indexOf(source)
                        val targetIndex = nodes.indexOf(target)
                        if (sourceIndex != -1 && targetIndex != -1) {
                            drawLine(
                                    color = Color(0xFF4A0072).copy(alpha = 0.3f),
                                    start = nodePositions[sourceIndex],
                                    end = nodePositions[targetIndex],
                                    strokeWidth = 4f
                            )
                        }
                    }

                    // Dibujar nodos
                    nodePositions.forEach { position ->
                        drawCircle(
                                color = Color(0xFF7B1FA2),
                                center = position,
                                radius = 30f,
                                style = Stroke(width = 4f)
                        )

                        drawCircle(color = Color(0xFFBA68C8), center = position, radius = 26f)
                    }
                    // dibujar texto
                    nodePositions.forEachIndexed { index, position ->
                        drawContext.canvas.nativeCanvas.apply {
                            drawText(
                                    File(nodes[index]).name,
                                    position.x - 20f,
                                    position.y + 8f,
                                    android.graphics.Paint().apply {
                                        color = android.graphics.Color.GRAY
                                        textSize = 42f
                                        textAlign = android.graphics.Paint.Align.CENTER
                                    }
                            )
                        }
                    }
                }
            }
        }

        Text(
                text = "Grafo",
                fontSize = 40.sp,
                color = Color(0xFF4A0072),
                modifier = Modifier.align(Alignment.TopStart).padding(16.dp)
        )
    }
}

private fun calculateNodePositions(
        nodeCount: Int,
        context: Context,
): List<Offset> {
    val displayMetrics = context.resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels.toFloat()
    val screenHeight = displayMetrics.heightPixels.toFloat()

    return List(nodeCount) { index ->
        val angle = (2 * Math.PI * index / nodeCount).toFloat()
        val radius = minOf(screenWidth, screenHeight) * 0.3f
        Offset(
                x = screenWidth / 2 + radius * cos(angle),
                y = screenHeight / 2 + radius * sin(angle)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Previeww() {
    VerGrafo()
}
