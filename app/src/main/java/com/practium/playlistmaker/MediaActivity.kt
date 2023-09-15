package com.practium.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class MediaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media)

        val backToMainMenu = findViewById<TextView>(R.id.activity_media_header)
        backToMainMenu.setOnClickListener {
            finish()
        }
    }
}