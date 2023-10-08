package com.practium.playlistmaker

import android.app.Application
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import com.google.gson.Gson

class App : Application() {

    var darkTheme = false

    private var trackHistory = ArrayList<Track>()


    override fun onCreate() {
        super.onCreate()

        val darkTheme =
            getSharedPreferences().getBoolean(SharedPreferencesConstants.DARK_THEME_KEY, darkTheme)
        switchTheme(darkTheme)

        trackHistory = getTrackHistoryFromSharedPref()

    }

    fun switchTheme(darkThemeEnabled: Boolean) {

        darkTheme = darkThemeEnabled

        AppCompatDelegate.setDefaultNightMode(
            if (darkThemeEnabled) {
                AppCompatDelegate.MODE_NIGHT_YES
            } else
                AppCompatDelegate.MODE_NIGHT_NO
        )
    }

    private fun getSharedPreferences(): SharedPreferences {
        return getSharedPreferences(SharedPreferencesConstants.USER_PREFERENCES, MODE_PRIVATE)
    }


    fun addTrackToHistory(track: Track) {

        if (trackHistory.size > 9) {
            trackHistory.removeAt(0)
        }

        val trackHistoryIterator = trackHistory.iterator()
        while (trackHistoryIterator.hasNext()) {
            val trackInList = trackHistoryIterator.next()
            if (trackInList.trackId== track.trackId) {
                trackHistoryIterator.remove()
            }
        }

        trackHistory.add(track)
    }

    fun clearTrackHistory() {
        trackHistory.clear()
        val json = Gson().toJson(trackHistory.toArray())
        getSharedPreferences().edit()
            .putString(SharedPreferencesConstants.SEARCH_KEY, json)
            .apply()
    }

    fun saveTrackHistory() {
        val json = Gson().toJson(trackHistory.toArray())
        getSharedPreferences().edit()
            .putString(SharedPreferencesConstants.SEARCH_KEY, json)
            .apply()
    }

    fun getTrackHistoryFromSharedPref(): ArrayList <Track> {
        val json = getSharedPreferences().getString(
            SharedPreferencesConstants.SEARCH_KEY,
            null
         ) ?: return emptyArray<Track>().toCollection(ArrayList())

        val trackArray = Gson().fromJson(json, Array<Track>::class.java).toCollection(ArrayList())

        trackArray.reverse()
        return trackArray
    }

}