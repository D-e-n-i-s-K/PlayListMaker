package com.practium.playlistmaker

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class SettingsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backToMainMenu = findViewById<TextView>(R.id.activity_setting_header)
        val shareAppButton = findViewById<Button>(R.id.activity_settings_share_app_button)


        backToMainMenu.setOnClickListener {
            finish()
        }

        shareAppButton.setOnClickListener {

            val shareAppDisplay = Intent(Intent.ACTION_SEND)
            shareAppDisplay.data = Uri.parse("https:")
            shareAppDisplay.putExtra(Intent.EXTRA_TEXT, "https://practicum.yandex.ru/android-developer/?from=catalog")
            startActivity(shareAppDisplay)
        }


    }
}