package com.practium.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.RoundedCorner
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

    private lateinit var searchText: String
    private var searchEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val trackList = getTestTrackList()

        val trackListRecyclerView = findViewById<RecyclerView>(R.id.trackList)
        trackListRecyclerView.layoutManager = LinearLayoutManager(this)
        trackListRecyclerView.adapter = TrackAdapter(trackList)

        val cancelButton = findViewById<ImageView>(R.id.cancelButton)
        cancelButton.visibility = View.GONE

        searchEditText = findViewById<EditText>(R.id.search_editeText)

        cancelButton.setOnClickListener {
            searchEditText?.text?.clear()

            // Покажем клавиатуру только если в фокусе поле Поиска
            this.currentFocus?.let { view ->
                val inputMethodManager =
                    getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
            }
        }

        val searchEditTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cancelButton.visibility = clearCancelVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }

        }
        searchEditText?.addTextChangedListener(searchEditTextWatcher)

        val backToMainActivityButton = findViewById<TextView>(R.id.activity_search_header)
        backToMainActivityButton.setOnClickListener {
            finish()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchEditText?.text.toString()
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT,"")
        searchEditText?.setText(searchText)

    }

    fun clearCancelVisibility(s: CharSequence?): Int {

        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun getTestTrackList(): ArrayList<Track> {

        return arrayListOf(
            Track(
                getString(R.string.test_trackName_1),
                getString(R.string.test_artistName_1),
                getString(R.string.test_trackTime_1),
                getString(R.string.test_artworkUrl100_1),
            ),
            Track(
                getString(R.string.test_trackName_2),
                getString(R.string.test_artistName_2),
                getString(R.string.test_trackTime_2),
                getString(R.string.test_artworkUrl100_2),
            ),
            Track(
                getString(R.string.test_trackName_3),
                getString(R.string.test_artistName_3),
                getString(R.string.test_trackTime_3),
                getString(R.string.test_artworkUrl100_3),
            ),
            Track(
                getString(R.string.test_trackName_4),
                getString(R.string.test_artistName_4),
                getString(R.string.test_trackTime_4),
                getString(R.string.test_artworkUrl100_4),
            ),
            Track(
                getString(R.string.test_trackName_5),
                getString(R.string.test_artistName_5),
                getString(R.string.test_trackTime_5),
                getString(R.string.test_artworkUrl100_5),
            ),

        )
    }
}