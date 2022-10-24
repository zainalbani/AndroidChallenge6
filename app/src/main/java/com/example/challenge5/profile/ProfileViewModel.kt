package com.example.challenge5.profile

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.challenge5.database.RegisterDatabase
import com.example.challenge5.database.RegisterEntity
import com.example.challenge5.database.RegisterRepository
import com.example.challenge5.repository.UserManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProfileViewModel (application: Application) : AndroidViewModel(application){
    val repository: RegisterRepository

    init {
        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao
        repository = RegisterRepository(dao)
    }

    fun updateUser (user: RegisterEntity) = viewModelScope.launch(Dispatchers.IO) {
        repository.update(user)
    }

}