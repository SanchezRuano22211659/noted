package com.ozg.noted

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ozg.noted.core.FileManager
import dev.jeziellago.compose.markdowntext.MarkdownText
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditorScreen(fileName: String, navController: NavController? = null) {
    val context = LocalContext.current
    val file = remember { File(FileManager.getBaseDir(), fileName) }
    var content by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        content = if (file.exists()) file.readText() else ""
    }

    Scaffold(
            floatingActionButton = {
                // Botón para guardar
                androidx.compose.material3.FloatingActionButton(
                        onClick = {
                            FileManager.createNote(fileName, content)
                            navController?.popBackStack()
                        }
                ) { Text("💾") }
            }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            // Editor Markdown
            androidx.compose.material3.TextField(
                    value = content,
                    onValueChange = { content = it },
                    modifier = Modifier.fillMaxSize().weight(1f),
                    placeholder = { Text("Escribe tu nota en Markdown...") }
            )

            MarkdownText(
                    markdown = content,
                    modifier = Modifier.fillMaxSize().weight(1f).padding(16.dp)
            )
        }
    }

    file.writeText(content)
}

@Preview(showBackground = true)
@Composable
fun EditorPreview() {
    EditorScreen(fileName = "white.md", navController = rememberNavController())
}
