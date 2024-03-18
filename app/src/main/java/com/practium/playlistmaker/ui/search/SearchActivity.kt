package com.practium.playlistmaker.ui.search

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.practium.playlistmaker.Creator
import com.practium.playlistmaker.R
import com.practium.playlistmaker.domain.api.TracksInteractor
import com.practium.playlistmaker.domain.models.Track
import com.practium.playlistmaker.ui.player.PlayerActivity
import java.io.Serializable


class SearchActivity : Activity() {

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }

    private val tracksInteractor = Creator.provideTracksInteractor()
    private lateinit var searchInput: EditText
    private lateinit var searchText: String
    private val handler = Handler(Looper.getMainLooper())
    private val searchRunnable = Runnable { searchRequest() }
    private val tracks = ArrayList<Track>()
    private var isClickAllowed = true
    private lateinit var tracksList: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var clearSearchEditTextViewButton: ImageView
    private lateinit var backToMainActivityButton: TextView

    private val adapter = TracksAdapter {
        if (clickDebounce()) {

            val intentPlayer = Intent(this, PlayerActivity::class.java)
            intentPlayer.putExtra("trackName", it.trackName)
            intentPlayer.putExtra("artistName", it.artistName)
            intentPlayer.putExtra("trackTimeMillis", it.trackTimeMillis)
            intentPlayer.putExtra("artworkUrl100", it.artworkUrl100)
            intentPlayer.putExtra("artworkUrl512", it.artworkUrl512)
            intentPlayer.putExtra("collectionName", it.collectionName)
            intentPlayer.putExtra("releaseDate", it.releaseDate)
            intentPlayer.putExtra("primaryGenreName", it.primaryGenreName)
            intentPlayer.putExtra("country", it.country)
            intentPlayer.putExtra("previewUrl", it.previewUrl)

            startActivity(intentPlayer)

//            val intentPlayer = Intent(this, PlayerActivity::class.java)
//            intentPlayer.putExtra("track", it)
//            startActivity(intentPlayer)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        searchInput = findViewById(R.id.search_editeText)
        tracksList = findViewById(R.id.trackList)
        progressBar = findViewById(R.id.progressBar)
        clearSearchEditTextViewButton = findViewById(R.id.clearSearchEditTextViewButton)
        backToMainActivityButton = findViewById(R.id.activity_search_header)

        adapter.trackList = tracks
        tracksList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        tracksList.adapter = adapter

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                searchDebounce()
            }

            override fun afterTextChanged(p0: Editable?) {
            }

        })

        backToMainActivityButton.setOnClickListener {
            finish()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchInput.text.toString()
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT, "")
        searchInput.setText(searchText)
    }

    private fun searchRequest() {
        if (searchInput.text.isNotEmpty()) {

//            placeholderMessage.visibility = View.GONE
//            moviesList.visibility = View.GONE
            progressBar.visibility = View.VISIBLE

            tracksInteractor.searchTracks(
                searchInput.text.toString(),
                object : TracksInteractor.TacksConsumer {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun consume(foundTracks: List<Track>) {
                        handler.post {
                            progressBar.visibility = View.GONE
                            tracks.clear()
                            tracks.addAll(foundTracks)
                            tracksList.visibility = View.VISIBLE
                            adapter.notifyDataSetChanged()
                            clearSearchEditTextViewButton.visibility = View.VISIBLE
                            if (tracks.isEmpty()) {
//                            showMessage(getString(R.string.nothing_found), "")
                            } else {
//                            hideMessage()
                            }
                        }
                    }
                })
        }
    }

    private fun clickDebounce(): Boolean {

        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    private fun searchDebounce() {
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }
}
