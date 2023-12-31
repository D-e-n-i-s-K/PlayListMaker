package com.practium.playlistmaker

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class TrackAdapter(private val onTrackClickListener: SearchActivity) : RecyclerView.Adapter<TrackHolder>() {

    var trackList = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_card, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener {
            onTrackClickListener.onTrackItemClickListener(trackList[position])
        }
    }

    override fun getItemCount(): Int {
        return trackList.size
    }


}
