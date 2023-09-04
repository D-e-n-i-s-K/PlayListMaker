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
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        val cancelButton = findViewById<ImageView>(R.id.cancelButton)
        cancelButton.visibility = View.GONE

        val searchEditText = findViewById<EditText>(R.id.search_editeText)

        cancelButton.setOnClickListener{
            searchEditText.text.clear()

            // Only runs if there is a view that is currently focused
            this.currentFocus?.let { view ->
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                imm?.hideSoftInputFromWindow(view.windowToken, 0)

            }
        }

        val searchEditTextWatcher = object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                cancelButton.visibility = clearCancelVisibility(s)
            }

            override fun afterTextChanged(s: Editable?) {

            }

        }
        searchEditText.addTextChangedListener(searchEditTextWatcher)

        val backToMainActivityButton = findViewById<TextView>(R.id.activity_search_header)
        backToMainActivityButton.setOnClickListener {
            finish()
        }


    }
    fun clearCancelVisibility(s: CharSequence?): Int{

        return if (s.isNullOrEmpty()) {View.GONE}
        else {View.VISIBLE}

    }
}