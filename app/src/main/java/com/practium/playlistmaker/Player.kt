package com.practium.playlistmaker

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners


class Player : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        var backToSearchMenuButton = findViewById<ImageView>(R.id.backArrow)

        val trackNameView = findViewById<TextView>(R.id.trackName)
        val artistNameView = findViewById<TextView>(R.id.artistName)
        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        val releaseDateView = findViewById<TextView>(R.id.releaseDateValue)
        val collectionNameView = findViewById<TextView>(R.id.collectionName)
        val primaryGenreNameView = findViewById<TextView>(R.id.primaryGenreName)
        val countryView = findViewById<TextView>(R.id.country)
        val artworkUrl512View = findViewById<ImageView>(R.id.artworkUrl512)

        val playDurationValue = findViewById<TextView>(R.id.playDurationValue)
        playDurationValue.text = "0:29"

        val app = (application as App)

        backToSearchMenuButton.setOnClickListener {
            finish()
        }

        val intent = intent
        trackNameView.text = intent.getStringExtra("trackName")
        artistNameView.text = intent.getStringExtra("artistName")

        val trackTimeMillisLong = intent.getLongExtra("trackTimeMillis", 0)
        trackTimeMillis.text = app.milliSecToMMSS(trackTimeMillisLong)

        releaseDateView.text = intent.getStringExtra("releaseDate")?.substring(0, 4)
        collectionNameView.text = intent.getStringExtra("collectionName")
        primaryGenreNameView.text = intent.getStringExtra("primaryGenreName")
        countryView.text = intent.getStringExtra("country")

        Glide.with(this)
            .load(intent.getStringExtra("artworkUrl512"))
            .placeholder(R.drawable.placeholder_player_album_title)
            .centerCrop()
            .transform(RoundedCorners(app.dpToPix(2F, this)))
            .into(artworkUrl512View)
    }
}

