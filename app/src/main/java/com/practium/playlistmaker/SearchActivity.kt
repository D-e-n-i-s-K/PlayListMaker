package com.practium.playlistmaker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SearchActivity : AppCompatActivity() {

    companion object {
        const val SEARCH_TEXT = "SEARCH_TEXT"
    }

    private lateinit var searchText: String
    private var searchEditText: EditText? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

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
}