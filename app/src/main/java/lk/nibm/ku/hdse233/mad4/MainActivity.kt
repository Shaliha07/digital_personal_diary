package lk.nibm.ku.hdse233.mad4

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    private lateinit var notes: EditText
    private lateinit var memos: EditText
    private lateinit var mood: Spinner
    private lateinit var tasks: EditText
    private lateinit var experiences: EditText
    private lateinit var btnSave: Button
    private lateinit var btnDelete: Button
    private lateinit var dbHelper: DbHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        dbHelper = DbHelper(this)

        // Initialize UI components
        notes = findViewById(R.id.notes)
        memos = findViewById(R.id.memos)
        mood = findViewById(R.id.mood)
        tasks = findViewById(R.id.tasks)
        experiences = findViewById(R.id.experiences)
        btnSave = findViewById(R.id.btnSave)
        btnDelete = findViewById(R.id.btnDelete)

        btnSave.setOnClickListener {
            val task = Task(
                id = 0,
                notes = notes.text.toString(),
                memos = memos.text.toString(),
                mood = mood.selectedItem.toString(),
                tasks = tasks.text.toString(),
                experiences = experiences.text.toString()
            )
            dbHelper.insertTask(task)
        }

        // Delete button logic
        btnDelete.setOnClickListener {
            // Replace 1 with the actual task ID to delete
            dbHelper.deleteTask(1)
        }
    }
}