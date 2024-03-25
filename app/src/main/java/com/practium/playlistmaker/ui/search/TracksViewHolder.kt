package com.practium.playlistmaker.ui.search

import android.content.Context
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practium.playlistmaker.R
import com.practium.playlistmaker.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class TracksViewHolder(parent: ViewGroup) :
    RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.track_card, parent, false)) {

    var trackName: TextView = itemView.findViewById(R.id.track_name)
    var artistName: TextView = itemView.findViewById(R.id.artist_name)
    var trackTime: TextView = itemView.findViewById(R.id.track_time)
    var artwork: ImageView = itemView.findViewById(R.id.artwork)
//    var artworkUrl512: TextView = itemView.findViewById(R.id.artworkUrl512)
//    var collectionName: TextView = itemView.findViewById(R.id.collectionName)

    fun bind(track: Track) {
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .placeholder(R.drawable.placeholder)
            .centerCrop()
            .into(artwork)

        trackName.text = track.trackName
        artistName.text = track.artistName
        trackTime.text = track.trackTimeMillis.toString()
//        artworkUrl512.text =  track.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
    }
}
//
//class TracksViewHolder(private val view: View) : RecyclerView.ViewHolder(view){
//
//    private val trackName: TextView = view.findViewById(R.id.track_name)
//    private val artistName: TextView = view.findViewById(R.id.artist_name)
//    private val trackTime: TextView = view.findViewById(R.id.track_time)
//    private val artwork: ImageView = view.findViewById(R.id.artwork)
//    private var collectionName: String = ""
//    private var releaseDate: String = ""
//    private var primaryGenreName: String = ""
//    private var country: String = ""
//    private var artworkUrl512: String = ""
//    private var previewUrl: String = ""
//
//
//    fun bind(model: Track) {
//        trackName.text = model.trackName
//        artistName.text = model.artistName
//        trackTime.text = milliSecToMMSS(model.trackTimeMillis) // model.trackTimeMillis.toString()
//        Glide.with(view)
//            .load(model.artworkUrl100)
//            .placeholder(R.drawable.placeholder)
//            .centerCrop()
//            .transform(RoundedCorners(dpToPix(2F, view.context)))
//            .into(artwork)
//
//        collectionName = model.collectionName
//        releaseDate = model.releaseDate
//        primaryGenreName = model.primaryGenreName
//        country = model.country
//        artworkUrl512 = model.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
//        previewUrl = model.previewUrl
//    }
//
//    private fun dpToPix(dp: Float, context: Context): Int {
//        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.resources.displayMetrics).toInt()
//    }
//
//    private fun milliSecToMMSS(milliSeconds: Long): String{
//        return  SimpleDateFormat("mm:ss", Locale.getDefault()).format(milliSeconds)
//    }
//
//
//}