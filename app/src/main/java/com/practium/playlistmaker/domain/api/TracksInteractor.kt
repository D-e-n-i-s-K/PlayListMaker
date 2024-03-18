package com.practium.playlistmaker.domain.api

import com.practium.playlistmaker.domain.models.Track

interface TracksInteractor {
    fun searchTracks(expression: String, consumer: TacksConsumer)

    interface TacksConsumer {
        fun consume(foundTracks: List<Track>)
    }
}