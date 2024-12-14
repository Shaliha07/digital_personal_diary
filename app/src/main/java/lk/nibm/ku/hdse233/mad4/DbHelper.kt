package lk.nibm.ku.hdse233.mad4

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "digital_diary.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "diary_entries"
        const val COLUMN_ID = "id"
        const val COLUMN_NOTES = "notes"
        const val COLUMN_MEMOS = "memos"
        const val COLUMN_MOOD = "mood"
        const val COLUMN_TASKS = "tasks"
        const val COLUMN_EXPERIENCES = "experiences"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val query = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NOTES TEXT,
                $COLUMN_MEMOS TEXT,
                $COLUMN_MOOD TEXT,
                $COLUMN_TASKS TEXT,
                $COLUMN_EXPERIENCES TEXT
            )
        """
        db?.execSQL(query)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }

    fun insertTask(task: Task): Boolean {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOTES, task.notes)
            put(COLUMN_MEMOS, task.memos)
            put(COLUMN_MOOD, task.mood)
            put(COLUMN_TASKS, task.tasks)
            put(COLUMN_EXPERIENCES, task.experiences)
        }
        db.insert(TABLE_NAME, null, contentValues)
        return true
    }

    fun deleteTask(id: Int): Boolean {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        return true
    }

    fun selectTasks(): List<Task> {
        val db = readableDatabase
        val cursor: Cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        val tasks: MutableList<Task> = mutableListOf()
        while (cursor.moveToNext()) {
            val task = Task(
                id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                notes = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOTES)),
                memos = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMOS)),
                mood = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MOOD)),
                tasks = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TASKS)),
                experiences = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPERIENCES))
            )
            tasks.add(task)
        }
        cursor.close()
        return tasks
    }
}