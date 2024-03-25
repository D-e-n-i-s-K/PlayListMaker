package com.practium.playlistmaker.data

import android.media.MediaPlayer
import com.practium.playlistmaker.domain.api.PlayerRepository

class PlayerRepositoryImpl : PlayerRepository {

    private val mediaPlayer = MediaPlayer()

    override fun setDataSource(dataSource: String) {
        mediaPlayer.setDataSource(dataSource)
    }

    override fun prepareAsync() {
        mediaPlayer.prepareAsync()
    }

    override fun setOnPreparedListener(onPreparedListener: () -> Unit) {
        mediaPlayer.setOnPreparedListener {
            onPreparedListener()
        }
    }

    override fun setOnCompletionListener(onCompletionListener: () -> Unit) {
        mediaPlayer.setOnCompletionListener {
            onCompletionListener()
        }
    }

    override fun start() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun currentPosition(): Int {
        return mediaPlayer.currentPosition
    }
}