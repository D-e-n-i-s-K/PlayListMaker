package com.practium.playlistmaker

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import java.text.SimpleDateFormat
import java.util.*

class TrackHolder(private val view: View) : RecyclerView.ViewHolder(view){

    private val trackName : TextView = view.findViewById(R.id.track_name)
    private val artistName : TextView = view.findViewById(R.id.artist_name)
    private val trackTime : TextView = view.findViewById(R.id.track_time)
    private val artwork : ImageView = view.findViewById(R.id.artwork)

    fun bind(model: Track){

        trackName.text = model.trackName
        artistName.text = model.artistName
        trackTime.text = milliSecToMMSS(model.trackTimeMillis) // model.trackTimeMillis.toString()
        Glide.with(view)
            .load(model.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .transform(RoundedCorners(dpToPix(2F, view.context)))
            .into(artwork)

    }

    private fun dpToPix(dp: Float, context: Context): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
    }

    private fun milliSecToMMSS(milliSeconds: Long): String{
        return  SimpleDateFormat("mm:ss", Locale.getDefault()).format(milliSeconds)
    }

}