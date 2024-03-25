package com.practium.playlistmaker.domain.api

import com.practium.playlistmaker.domain.models.Track

interface TracksRepository {
    fun searchTracks(expression: String): List<Track>
}