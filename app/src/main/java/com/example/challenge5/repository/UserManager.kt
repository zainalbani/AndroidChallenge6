package com.example.challenge5.repository

import android.content.Context
import androidx.datastore.DataStore
import androidx.datastore.preferences.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UserManager(context : Context) {

    private val dataStore: DataStore<Preferences> = context.createDataStore(name = "user_pref")

    companion object {
        val USERNAME = preferencesKey<String>("USERNAME")
        val PROFILE_IMAGE_KEY = preferencesKey<String>("PROFILE_IMAGE_KEY")
        val ID_USER_KEY = preferencesKey<Int>("ID_USER_KEY")

    }

    suspend fun saveData(username: String) {
        dataStore.edit {
            it[USERNAME] = username
        }
    }
    suspend fun saveProfileImage(uri: String){
        dataStore.edit { preferences ->
            preferences[PROFILE_IMAGE_KEY] = uri
        }
    }

    suspend fun clearData() {
        dataStore.edit {
            it.clear()
        }
    }

    val userNameFlow: Flow<String> = dataStore.data.map {
        it[USERNAME] ?: ""
    }
    val getId : Flow<Int> = dataStore.data.map {
        it[ID_USER_KEY] ?: 0
    }
    val getUserImageProfile : Flow<String> = dataStore.data.map{
        it[PROFILE_IMAGE_KEY] ?: ""
    }
}