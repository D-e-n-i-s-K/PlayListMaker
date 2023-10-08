package com.practium.playlistmaker

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SearchActivity() : AppCompatActivity(), OnTrackClickListener {

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

    class SearchResultProblem {
        companion object {
            const val NO_PROBLEM = "no_problem"
            const val NO_SEARCH_RESULT = "no_search_result"
            const val CONNECTION_PROBLEM = "connection_problem"
        }
    }

    private val itunesUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesApiService = retrofit.create(ItunesApiService::class.java)

    private lateinit var searchText: String
    private  var lastBadSearchText: String = ""
    private var searchEditTextView: EditText? = null
    private var trackList = ArrayList<Track>()
    private lateinit var searchHistoryTitle: TextView
    private lateinit var clearHistoryButton: Button
    private lateinit var refreshButton: Button
    private lateinit var placeHolder: LinearLayout
    private lateinit var placeHolderText: TextView
    private lateinit var backToMainActivityButton: TextView
    private lateinit var placeHolderIcon: ImageView

    private lateinit var clearSearchEditTextViewButton: ImageView

    private lateinit var trackListRecyclerView: RecyclerView

    private val adapter = TrackAdapter(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_search)

        refreshButton = findViewById(R.id.refreshButton)
        searchHistoryTitle = findViewById(R.id.searchHistoryTitle)
        clearHistoryButton = findViewById(R.id.clearHistoryButton)
        placeHolder = findViewById(R.id.placeHolder)
        placeHolderText = findViewById(R.id.placeHolderText)
        placeHolderIcon = findViewById(R.id.placeHolderIcon)
        backToMainActivityButton = findViewById(R.id.activity_search_header)
        clearSearchEditTextViewButton = findViewById(R.id.clearSearchEditTextViewButton)
        searchEditTextView = findViewById(R.id.search_editeText)
        trackListRecyclerView = findViewById(R.id.trackList)

        clearHistoryButton.setOnClickListener {
            (application as App).clearTrackHistory()
            adapter.trackList.clear()
            adapter.notifyDataSetChanged()
            showHideHistory(true, it)
        }

        adapter.trackList = trackList
        trackListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        trackListRecyclerView.adapter = adapter

        searchEditTextView?.setOnEditorActionListener { view, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var text = searchEditTextView!!.text.toString()
                if (!text.isNullOrEmpty()) searchItunes(text, view)
                true
            } else {
                false
            }
        }

        searchEditTextView?.setOnFocusChangeListener { view, hasFocus ->
            if(hasFocus && searchEditTextView!!.text.isNullOrEmpty()){
               showHideHistory(true, view)
            } else {
               showHideHistory(false, view)
            }
        }

        clearSearchEditTextViewButton.setOnClickListener {
            adapter.trackList.clear()
            adapter.notifyDataSetChanged()
            searchEditTextView?.text?.clear()

            placeHolder.visibility = View.GONE

            this.currentFocus?.let { view ->
                showHideHistory(true, view)
            }
        }

        val searchEditTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                clearSearchEditTextViewButton.visibility = clearCancelVisibility(s)
                searchText = s.toString()

                if (searchEditTextView?.hasFocus()!! && s?.isEmpty() == true) {
                    showHideHistory(true, searchEditTextView!!)
                    placeHolder.visibility = View.GONE
                }
            }
            override fun afterTextChanged(s: Editable?) {
            }
        }
        searchEditTextView?.addTextChangedListener(searchEditTextWatcher)

        backToMainActivityButton.setOnClickListener {
            finish()
        }

        refreshButton.setOnClickListener {



            if (!lastBadSearchText.isNullOrEmpty()){
                searchEditTextView?.setText(lastBadSearchText)
                searchItunes(lastBadSearchText, it)
            } else
            if (!searchText.isNullOrEmpty()) {
                searchItunes(searchText, it)
            }
        }
    }

    fun showSearchResult(response: Response<TrackResponse>){
        placeHolder.visibility = View.GONE
        trackList.clear()
        adapter.trackList.addAll(response.body()?.results!!)
        adapter.notifyDataSetChanged()
    }

    private fun showHideHistory(show: Boolean, view: View) {

        var visible = View.GONE

        if (show) {
            var trackList: ArrayList<Track> = (application as App).getTrackHistoryFromSharedPref()
            if (trackList.size > 0) {
                adapter.trackList = trackList
                adapter.notifyDataSetChanged()
                hideKeyBoard(true, view)
                visible = View.VISIBLE
            }
        } else {
            adapter.trackList.clear()
            adapter.notifyDataSetChanged()
        }

        searchHistoryTitle.visibility = visible
        clearHistoryButton.visibility = visible
    }

    private fun searchItunes(text: String, view: View) {

        showHideHistory(false, view)

        itunesApiService.search(searchText).enqueue(
            object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.resultCount?.toInt() !== 0) {
                            showSearchResult(response)
                            showSearchResultProblemPlaceholder(SearchResultProblem.NO_PROBLEM, view)
                        } else {
                            showSearchResultProblemPlaceholder(SearchResultProblem.NO_SEARCH_RESULT, view)
                        }
                    } else {
                        showSearchResultProblemPlaceholder(SearchResultProblem.CONNECTION_PROBLEM, view)
                    }
                }
                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    showSearchResultProblemPlaceholder(SearchResultProblem.CONNECTION_PROBLEM, view)
                }
            }
        )

    }

    private fun showSearchResultProblemPlaceholder(problem: String, view: View){

        if (problem == SearchResultProblem.NO_PROBLEM) {
            placeHolder.visibility = View.GONE
            lastBadSearchText = ""
        } else {
                trackList.clear()
                adapter.notifyDataSetChanged()
                placeHolder.visibility = View.VISIBLE
            if (problem == SearchResultProblem.NO_SEARCH_RESULT) {
                placeHolderText.text = getString(R.string.searchNothing)
                placeHolderIcon.setImageDrawable( getDrawable(R.drawable.sadsmile))
                refreshButton.visibility = View.GONE
                hideKeyBoard(true, view)
            } else if (problem == SearchResultProblem.CONNECTION_PROBLEM) {
                lastBadSearchText = searchText
                placeHolderText.text = getString(R.string.noInternet)
                placeHolderIcon.setImageDrawable( getDrawable(R.drawable.nosignal))
                refreshButton.visibility = View.VISIBLE
                hideKeyBoard(true, view)
            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        searchText = searchEditTextView?.text.toString()
        outState.putString(SEARCH_TEXT, searchText)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        searchText = savedInstanceState.getString(SEARCH_TEXT,"")
        searchEditTextView?.setText(searchText)

    }

    fun clearCancelVisibility(s: CharSequence?): Int {

        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun hideKeyBoard(show: Boolean, view: View) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }



    override fun onTrackItemClickListener(track: Track) {

        // добавление в историю поиска по нажатию на эл. списка
        var app = (application as App)
        app.addTrackToHistory(track)
        app.saveTrackHistory()

        // открыть акивити Player
        val intentPlayer = Intent(this, Player::class.java)
        intentPlayer.putExtra("trackName", track.trackName)
        intentPlayer.putExtra("artistName", track.artistName)
        intentPlayer.putExtra("trackTimeMillis", track.trackTimeMillis)
        intentPlayer.putExtra("artworkUrl100", track.artworkUrl100)
        intentPlayer.putExtra("artworkUrl512", track.getCoverArtwork())
        intentPlayer.putExtra("collectionName", track.collectionName)
        intentPlayer.putExtra("releaseDate", track.releaseDate)
        intentPlayer.putExtra("primaryGenreName", track.primaryGenreName)
        intentPlayer.putExtra("country", track.country)

        startActivity(intentPlayer)
    }


}