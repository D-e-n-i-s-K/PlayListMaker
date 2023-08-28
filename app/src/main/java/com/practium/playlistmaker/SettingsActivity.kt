package com.practium.playlistmaker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainMenu = findViewById<TextView>(R.id.activity_setting_header)
        backToMainMenu.setOnClickListener {
            finish()
        }
    }
}