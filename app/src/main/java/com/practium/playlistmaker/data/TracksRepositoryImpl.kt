package com.practium.playlistmaker.data

import com.practium.playlistmaker.data.dto.TracksSearchRequest
import com.practium.playlistmaker.data.dto.TracksSearchResponse
import com.practium.playlistmaker.domain.api.TracksRepository
import com.practium.playlistmaker.domain.models.Track

class TracksRepositoryImpl (private val networkClient: NetworkClient) : TracksRepository {

    override fun searchTracks(expression: String): List<Track> {
        val response = networkClient.doRequest(TracksSearchRequest(expression))
        if (response.resultCode == 200) {
            return (response as TracksSearchResponse).results.map {
                Track(
                    it.trackId,
                    it.trackName,
                    it.artistName,
                    it.trackTimeMillis,
                    it.artworkUrl100,
                    it.collectionName,
                    it.releaseDate,
                    it.primaryGenreName,
                    it.country,
//                    it.artworkUrl512,
                    it.previewUrl
                ) }
        } else {
            return emptyList()
        }
    }
}

