package com.example.challenge5.repository

import androidx.datastore.preferences.preferencesKey

object UserData {

    val USERNAME = preferencesKey<String>("USERNAME")
    val PASSWORD = preferencesKey<String>("PASSWORD")
}