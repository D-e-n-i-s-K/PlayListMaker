package com.practium.playlistmaker.ui.player

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practium.playlistmaker.App
import com.practium.playlistmaker.Creator
import com.practium.playlistmaker.R
import com.practium.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale
import com.practium.playlistmaker.databinding.ActivityPlayerBinding


class PlayerActivity : AppCompatActivity() {

    lateinit var playDurationValue: TextView

    private lateinit var binding: ActivityPlayerBinding
    companion object {
//        private const val STATE_DEFAULT = 0
//        private const val STATE_PREPARED = 1
//        private const val STATE_PLAYING = 2
//        private const val STATE_PAUSED = 3
         private const val DELAY_MILLIS = 1000L
     }

//    private var playerState = STATE_DEFAULT
    private lateinit var playButton: ImageView
//    private var mediaPlayer = MediaPlayer()

    private var mainThreadHandler: Handler? = null

    private val handler = Handler(Looper.getMainLooper())

    private var player = Creator.providePlayerInteractor()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        binding = ActivityPlayerBinding.inflate(layoutInflater)

        val backToSearchMenuButton = findViewById<ImageView>(R.id.backArrow)

        val trackNameView = findViewById<TextView>(R.id.trackName)
        val artistNameView = findViewById<TextView>(R.id.artistName)
        val trackTimeMillis = findViewById<TextView>(R.id.trackTimeMillis)
        val releaseDateView = findViewById<TextView>(R.id.releaseDateValue)
        val collectionNameView = findViewById<TextView>(R.id.collectionName)
        val primaryGenreNameView = findViewById<TextView>(R.id.primaryGenreName)
        val countryView = findViewById<TextView>(R.id.country)
        val artworkUrl512View = findViewById<ImageView>(R.id.artworkUrl512)

        playButton = findViewById(R.id.playButton)

        mainThreadHandler = Handler(Looper.getMainLooper())

        playDurationValue = findViewById(R.id.playDurationValue)
        playDurationValue.text = "00:00"

        val app = (application as App)

        backToSearchMenuButton.setOnClickListener {
            player.release()
            finish()
        }

//        val track  = intent.getSerializableExtra("track") as Track
//        track

        val intent = intent
        trackNameView.text = intent.getStringExtra("trackName")
        artistNameView.text = intent.getStringExtra("artistName")
        val trackTimeMillisLong = intent.getLongExtra("trackTimeMillis", 0)
        trackTimeMillis.text = app.milliSecToMMSS(trackTimeMillisLong)
        releaseDateView.text = intent.getStringExtra("releaseDate")?.substring(0, 4)
        collectionNameView.text = intent.getStringExtra("collectionName")
        primaryGenreNameView.text = intent.getStringExtra("primaryGenreName")
        countryView.text = intent.getStringExtra("country")
        val previewUrl = intent.getStringExtra("previewUrl").toString()
        preparePlayer(previewUrl)

        Glide.with(this)
            .load(intent.getStringExtra("artworkUrl512"))
            .placeholder(R.drawable.placeholder_player_album_title)
            .centerCrop()
            .transform(RoundedCorners(app.dpToPix(2F, this)))
            .into(artworkUrl512View)

        playButton.setOnClickListener {
            player.playbackControl(
                { startPlayer() },
                { pausePlayer() }
            ) }

    }

    private fun preparePlayer(previewUrl: String) {
        player.preparePlayer(previewUrl,
            {
                playButton.setImageResource(R.drawable.play_button)
            },
            {
                handler.removeCallbacks(updateTimer())
                playButton.setImageResource(R.drawable.play_button)
                binding.playDurationValue.text = "00:00"
            })

    }

    private fun startPlayer() {
        player.startPlayer {
            playButton.setImageResource(R.drawable.pause_button)
            handler.post(
                updateTimer()
            )
        }
    }

    private fun pausePlayer() {
        player.pausePlayer{
            playButton.setImageResource(R.drawable.play_button)
            handler.removeCallbacks(updateTimer())
        }
    }
    /*
        private fun startPlayer() {
            mediaPlayer.start()
            playerState = STATE_PLAYING
            mainThreadHandler?.post(updateTimer())
            playButton.setImageDrawable(getDrawable(R.drawable.pause_button))
        }

        private fun pausePlayer() {
            mediaPlayer.pause()
            playerState = STATE_PAUSED
            playButton.setImageDrawable(getDrawable(R.drawable.play_button))
        }

        private fun playbackControl() {
            when (playerState) {
                STATE_PLAYING -> {
                    pausePlayer()
                }

                STATE_PREPARED, STATE_PAUSED -> {
                    startPlayer()
                }
            }
        }*/

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    private fun updateTimer(): Runnable {
        return object : Runnable {
            override fun run() {
                binding.playDurationValue.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(player.currentPosition())
                handler.postDelayed(this, DELAY_MILLIS)

//                val currentPosition = mediaPlayer.currentPosition
//                playDurationValue?.text = String.format(
//                    SimpleDateFormat("mm:ss", Locale.getDefault()).format(currentPosition)
//                        .toString()
//                )
//                mainThreadHandler?.postDelayed(this, DELAY_MILLIS)
            }
        }
    }


}

