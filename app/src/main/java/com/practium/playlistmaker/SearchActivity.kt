package com.practium.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SearchActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val backToMainActivityButton = findViewById<TextView>(R.id.activity_search_header)
        backToMainActivityButton.setOnClickListener {
            finish()
        }
    }
}