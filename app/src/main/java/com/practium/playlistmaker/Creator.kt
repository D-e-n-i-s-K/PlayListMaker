package com.practium.playlistmaker

import com.practium.playlistmaker.data.PlayerRepositoryImpl
import com.practium.playlistmaker.data.TracksRepositoryImpl
import com.practium.playlistmaker.data.network.RetrofitNetworkClient
import com.practium.playlistmaker.domain.api.PlayerInteractor
import com.practium.playlistmaker.domain.api.TracksInteractor
import com.practium.playlistmaker.domain.api.TracksRepository
import com.practium.playlistmaker.domain.impl.PlayerInteractorImpl
import com.practium.playlistmaker.domain.impl.TracksInteractorImpl

object Creator {
    private fun getTracksRepository(): TracksRepository {
        return TracksRepositoryImpl(RetrofitNetworkClient())
    }

    fun provideTracksInteractor(): TracksInteractor {
        return TracksInteractorImpl(getTracksRepository())
    }

////////

    fun providePlayerInteractor(): PlayerInteractor {
        return PlayerInteractorImpl(PlayerRepositoryImpl())
    }
}