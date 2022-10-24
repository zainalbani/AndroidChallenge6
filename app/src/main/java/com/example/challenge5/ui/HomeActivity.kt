package com.example.challenge5.ui

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.asLiveData
import com.example.challenge5.R
import com.example.challenge5.database.RegisterEntity
import com.example.challenge5.databinding.ActivityHomeBinding
import com.example.challenge5.login.LoginActivity
import com.example.challenge5.profile.ProfileActivity
import com.example.challenge5.repository.UserManager
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeActivity : AppCompatActivity(){
    private var _binding : ActivityHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var userManager :UserManager
    private lateinit var user: RegisterEntity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userManager = UserManager(this)
        showFragment()
        userManager.userNameFlow.asLiveData().observe(this,{
            binding.tvData.text = "Hi, $it"
        })
        binding.btnLogout.setOnClickListener{
            GlobalScope.launch{
                userManager.clearData()
                navigateLogin()
            }
        }
        binding.btnProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }
    }


    private fun showFragment() {
        val mFragmentManager = supportFragmentManager
        val mFragmentTransaction = mFragmentManager.beginTransaction()
        val mFragment = DataFragment()

        mFragmentTransaction.add(R.id.flData,mFragment).commit()
    }
    fun navigateLogin(){
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}