package com.practium.playlistmaker.data.network

import com.practium.playlistmaker.data.NetworkClient
import com.practium.playlistmaker.data.dto.Response
import com.practium.playlistmaker.data.dto.TracksSearchRequest
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitNetworkClient : NetworkClient {

    private val itunesBaseUrl = "https://itunes.apple.com"

    private val retrofit = Retrofit.Builder()
        .baseUrl(itunesBaseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itunesService = retrofit.create(ItunesApiService::class.java)
    override fun doRequest(dto: Any): Response {
        if (dto is TracksSearchRequest) {
            val resp = itunesService.searchTracks(dto.expression).execute()
            val body = resp.body() ?: Response()
            return body.apply { resultCode = resp.code() }
        } else {
            return Response().apply { resultCode = 400 }
        }
    }
}