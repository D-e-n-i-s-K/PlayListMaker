package com.practium.playlistmaker.data.network

import com.practium.playlistmaker.data.dto.TracksSearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ItunesApiService{
    @GET("/search?entity=song")
    fun searchTracks(@Query("term") text: String) : Call<TracksSearchResponse>
}