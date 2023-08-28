package com.practium.playlistmaker


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonSearch = findViewById<Button>(R.id.activity_main_button_search)
        val buttonMedia = findViewById<Button>(R.id.activity_main_button_media)
        val buttonSettings = findViewById<Button>(R.id.activity_main_button_settings)

        val buttonSearchClick : View.OnClickListener = object: View.OnClickListener{
            override fun onClick(v: View?) {
                val intentSearch = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intentSearch)
             }
        }
        buttonSearch.setOnClickListener(buttonSearchClick)

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