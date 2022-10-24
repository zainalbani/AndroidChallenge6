package com.example.challenge5.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.challenge5.R
import com.example.challenge5.database.RegisterDatabase
import com.example.challenge5.database.RegisterRepository
import com.example.challenge5.databinding.ActivityLoginBinding
import com.example.challenge5.register.RegisterActivity
import com.example.challenge5.repository.UserManager
import com.example.challenge5.ui.HomeActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    private lateinit var binding : ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    lateinit var userManager: UserManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this, R.layout.activity_login)
        userManager = UserManager(this)
        val application = requireNotNull(this).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = LoginViewModelFactory(repository, application)

        loginViewModel = ViewModelProvider(this, factory).get(LoginViewModel::class.java)

        binding.myLoginViewModel = loginViewModel

        binding.lifecycleOwner = this

        userManager.userNameFlow.asLiveData().observe(this,{
            if (it.length != 0){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }
        })

        loginViewModel.navigatetoRegister.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                navigateRegister()
                loginViewModel.doneNavigatingRegister()
            }
        })

        loginViewModel.errotoast.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoast()
            }
        })

        loginViewModel.errotoastUsername.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "User doesnt exist,please Register!", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastErrorEmail()
            }
        })

        loginViewModel.errorToastInvalidPassword.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please check your Password", Toast.LENGTH_SHORT).show()
                loginViewModel.donetoastInvalidPassword()
            }
        })

        loginViewModel.navigatetoHome.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                saveDataStore()
                loginViewModel.doneNavigatingHome()
            }
        })
    }


    private fun navigateRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        startActivity(intent)

    }

    private fun saveDataStore() {
        val username = binding.textInputEditTextEmail.text.toString()
        GlobalScope.launch {
            userManager.saveData(username)
        }
    }
}