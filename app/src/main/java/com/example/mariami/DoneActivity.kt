package com.example.mariami  // ← Change this to your actual package name

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class DoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_done)

        val btnGoFirst = findViewById<Button>(R.id.btnGoFirst)

        // "Go To First Page" clears the back stack and goes back to MainActivity
        btnGoFirst.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java).apply {
                // Clear everything so pressing back doesn't return to Payment/Done
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}