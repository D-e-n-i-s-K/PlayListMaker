package com.practium.playlistmaker.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practium.playlistmaker.R
import com.practium.playlistmaker.domain.models.Track


class TracksAdapter(private val clickListener: TrackClickListener) : RecyclerView.Adapter<TracksViewHolder>() {

    var trackList = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder = TracksViewHolder(parent)

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener {
            clickListener.onTrackClick(trackList[position])
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun interface TrackClickListener {
        fun onTrackClick(track: Track)
    }


}
