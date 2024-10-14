package com.example.simpletodo

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.simpletodo.model.container.AppContainer
import com.example.simpletodo.model.container.AppDataContainer

class SimpleToDoApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()

        container = AppDataContainer(context = this)
    }
}