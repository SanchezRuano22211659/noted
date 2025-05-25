package com.ozg.noted.core

import android.content.Context
import android.os.Environment
import java.io.File

object FileManager {
    private lateinit var baseDir: File

    fun initialize(context: Context) {
        baseDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "Noted")
        if (!baseDir.exists()) {
            baseDir.mkdirs()
        }
    }

    fun getBaseDir(): File = baseDir

    fun createNote(fileName: String, content: String): Boolean {
        val file = File(baseDir, "$fileName.md")
        return try {
            file.writeText(content)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun readNote(fileName: String): String {
        val file = File(baseDir, "$fileName.md")
        return if (file.exists()) {
            file.readText()
        } else {
            ""
        }
    }

    fun createFolder(folderName: String): Boolean {
        val folder = File(baseDir, folderName)
        return if (!folder.exists()) {
            folder.mkdir()
        } else false
    }

    fun listContents(directory: File = baseDir): List<File> {
        return directory.listFiles()?.toList() ?: emptyList()
    }
}
