package com.practium.playlistmaker.domain.impl

import com.practium.playlistmaker.domain.api.TracksInteractor
import com.practium.playlistmaker.domain.api.TracksRepository

class TracksInteractorImpl (private val repository: TracksRepository) : TracksInteractor {

    override fun searchTracks(expression: String, consumer: TracksInteractor.TacksConsumer) {

        val t = Thread {
            consumer.consume(repository.searchTracks(expression))
        }
        t.start()
    }

}

