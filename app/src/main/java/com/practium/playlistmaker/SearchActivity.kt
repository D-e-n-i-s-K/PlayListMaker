package com.practium.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.collections.ArrayList

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

    private val itunesUrl = "https://itunes.apple.com"
    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val itunesApiService = retrofit.create(ItunesApiService::class.java)
    private lateinit var searchText: String
    private var searchEditText: EditText? = null
    private var  trackList = ArrayList<Track>()
    private val adapter = TrackAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val trackListRecyclerView = findViewById<RecyclerView>(R.id.trackList)
        val cancelButton = findViewById<ImageView>(R.id.cancelButton)
        val backToMainActivityButton = findViewById<TextView>(R.id.activity_search_header)

        searchEditText = findViewById(R.id.search_editeText)
        adapter.trackList = trackList

        trackListRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        trackListRecyclerView.adapter = adapter
        cancelButton.visibility = View.GONE

        searchEditText?.setOnEditorActionListener { view, actionId, _ ->

            if (actionId == EditorInfo.IME_ACTION_DONE) {
                var text = searchEditText!!.text.toString()
                if (!text.isNullOrEmpty()) searchItunes(text, view)
                true
            } else {
                false
            }
        }

        cancelButton.setOnClickListener {
            adapter.trackList.clear()
            adapter.notifyDataSetChanged()
            searchEditText?.text?.clear()

            this.currentFocus?.let { view ->
                hideKeyBoard(true, view)
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

        backToMainActivityButton.setOnClickListener {
            finish()
        }
    }

    private fun searchItunes(text: String, view: View) {

        val placeHolder = findViewById<LinearLayout>(R.id.placeHolder)
        val placeHolderText = findViewById<TextView>(R.id.placeHolderText)
        val placeHolderIcon = findViewById<ImageView>(R.id.placeHolderIcon)
        val refreshButton = findViewById<Button>(R.id.refreshButton)

        refreshButton.setOnClickListener {
            searchItunes(text, view)
        }

        fun noConnectionAction(){
            trackList.clear()
            adapter.notifyDataSetChanged()
            hideKeyBoard(true, view)

            placeHolderText.text = getString(R.string.noInternet)
            placeHolderIcon.setImageDrawable( getDrawable(R.drawable.nosignal))
            placeHolder.visibility = View.VISIBLE
            refreshButton.visibility = View.VISIBLE

        }

        fun noResAction(){
            trackList.clear()
            adapter.notifyDataSetChanged()
            hideKeyBoard(true, view)

            placeHolderText.text = getString(R.string.searchNothing)
            placeHolderIcon.setImageDrawable( getDrawable(R.drawable.sadsmile))
            placeHolder.visibility = View.VISIBLE
            refreshButton.visibility = View.GONE
        }

        itunesApiService.search(text).enqueue(
            object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    if (response.code() == 200) {
                        if (response.body()?.resultCount?.toInt() !== 0) {
                            placeHolder.visibility = View.GONE
                            trackList.clear()
                            adapter.trackList.addAll(response.body()?.results!!)
                            adapter.notifyDataSetChanged()
                        } else {
                            noResAction()
                        }
                    } else {
                        noConnectionAction()
                    }
                }
                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    noConnectionAction()
                }
            }
        )
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

    private fun hideKeyBoard(show: Boolean, view: View) {
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(view.windowToken, 0)
    }

}