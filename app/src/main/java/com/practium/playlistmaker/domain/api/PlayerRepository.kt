package com.practium.playlistmaker.domain.api

interface PlayerRepository {
    fun setDataSource(dataSource: String)
    fun prepareAsync()
    fun setOnPreparedListener(onPreparedListener: () -> Unit)
    fun setOnCompletionListener(onCompletionListener: () -> Unit)
    fun start()
    fun pause()
    fun release()
    fun currentPosition(): Int
}