package com.practium.playlistmaker.ui.main


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.practium.playlistmaker.ui.media.MediaActivity
import com.practium.playlistmaker.R
import com.practium.playlistmaker.ui.search.SearchActivity
import com.practium.playlistmaker.ui.setting.SettingsActivity


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.activity_main_button_search)
        val buttonMedia = findViewById<Button>(R.id.activity_main_button_media)
        val buttonSettings = findViewById<Button>(R.id.activity_main_button_settings)

        buttonSearch.setOnClickListener{
            val intentSearch = Intent(this@MainActivity, SearchActivity::class.java)
            startActivity(intentSearch)
        }

        buttonMedia.setOnClickListener {
            val intentMedia = Intent(this, MediaActivity:: class.java)
            startActivity(intentMedia)
        }

        buttonSettings.setOnClickListener {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivity(intent)
        }
    }



}