package com.ozg.noted.core

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.annotation.WorkerThread

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, "notes.db", null, 1) {

    @WorkerThread
    fun getAllNotes(): List<String> {
        val cursor = readableDatabase.rawQuery("SELECT id FROM $TABLE_NOTES", null)
        return mutableListOf<String>().apply {
            while (cursor.moveToNext()) {
                add(cursor.getString(0))
            }
            cursor.close()
        }
    }

    @WorkerThread
    fun getAllLinks(): List<Pair<String, String>> {
        val cursor = readableDatabase.rawQuery("SELECT source, target FROM $TABLE_LINKS", null)
        return mutableListOf<Pair<String, String>>().apply {
            while (cursor.moveToNext()) {
                add(Pair(cursor.getString(0), cursor.getString(1)))
            }
            cursor.close()
        }
    }

    companion object {
        const val TABLE_NOTES = "notes"
        const val TABLE_LINKS = "links"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(
                """
            CREATE TABLE $TABLE_NOTES (
                id TEXT PRIMARY KEY,
                title TEXT NOT NULL,
                path TEXT NOT NULL
            )
        """
        )

        db.execSQL(
                """
            CREATE TABLE $TABLE_LINKS (
                source TEXT NOT NULL,
                target TEXT NOT NULL,
                PRIMARY KEY (source, target),
                FOREIGN KEY(source) REFERENCES $TABLE_NOTES(id),
                FOREIGN KEY(target) REFERENCES $TABLE_NOTES(id)
            )
        """
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {}

    @WorkerThread
    fun insertNote(id: String, title: String, path: String) {
        writableDatabase.execSQL(
                "INSERT INTO $TABLE_NOTES (id, title, path) VALUES (?, ?, ?)",
                arrayOf(id, title, path)
        )
    }

    @WorkerThread
    fun insertLink(source: String, target: String) {
        writableDatabase.execSQL(
                "INSERT INTO $TABLE_LINKS (source, target) VALUES (?, ?)",
                arrayOf(source, target)
        )
    }

    @WorkerThread
    fun getLinkedNotes(noteId: String): List<String> {
        val cursor =
                readableDatabase.rawQuery(
                        "SELECT target FROM $TABLE_LINKS WHERE source = ?",
                        arrayOf(noteId)
                )
        return mutableListOf<String>().apply {
            while (cursor.moveToNext()) {
                add(cursor.getString(0))
            }
            cursor.close()
        }
    }
}
