package com.practium.playlistmaker.domain.models

import java.io.Serializable

data class Track (
    val trackId: Int, // Идентификатор
    val trackName: String, // Название композиции
    val artistName: String, // Имя исполнителя
    val trackTimeMillis: Long, // Продолжительность трека
    val artworkUrl100: String, // Ссылка на изображение обложки
    val collectionName: String, // Название альбома
    val releaseDate: String, // Год релиза трека
    val primaryGenreName: String, // Жанр трека
    val country: String, // Страна исполнителя
//    val artworkUrl512: String,  // Ссылка на изображение обложки
    val previewUrl: String  // Ссылка на отрывок

) {
    val artworkUrl512 = this.artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
}
//
//{
//    fun getCoverArtwork() = artworkUrl100.replaceAfterLast('/', "512x512bb.jpg")
//}
