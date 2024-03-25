package com.practium.playlistmaker.domain.api

interface PlayerInteractor{

    fun preparePlayer(
        dataSource: String,
        onPreparedPlayer: () -> Unit,
        onCompletionPlayer: () -> Unit
    )
    fun startPlayer(onStartPlayer: () -> Unit)
    fun pausePlayer(onPausePlayer: () -> Unit)
    fun playbackControl(onStartPlayer: () -> Unit, onPausePlayer: () -> Unit)
    fun release()
    fun currentPosition(): Int

}