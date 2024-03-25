package com.practium.playlistmaker.data

import com.practium.playlistmaker.data.dto.Response

interface NetworkClient {
    fun doRequest(dto: Any): Response
}