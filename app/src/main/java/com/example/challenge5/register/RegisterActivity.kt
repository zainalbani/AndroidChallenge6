package com.example.challenge5.register
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.challenge5.R
import com.example.challenge5.database.RegisterDatabase
import com.example.challenge5.database.RegisterRepository
import com.example.challenge5.databinding.ActivityRegisterBinding
import com.example.challenge5.login.LoginActivity

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)

        val application = requireNotNull(this).application

        val dao = RegisterDatabase.getInstance(application).registerDatabaseDao

        val repository = RegisterRepository(dao)

        val factory = RegisterViewModelFactory(repository, application)

        registerViewModel = ViewModelProvider(this, factory).get(RegisterViewModel::class.java)

        binding.myViewModel = registerViewModel

        binding.lifecycleOwner = this

        registerViewModel.navigateto.observe(this, Observer { hasFinished->
            if (hasFinished == true){
                Log.i("MYTAG","insidi observe")
                navigateLogin()
                registerViewModel.doneNavigating()
            }
        })

        registerViewModel.homeLiveData.observe(this, Observer {
            Log.i("MYTAG",it.toString()+"000000000000000000000000")
        })


        registerViewModel.errotoast.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoast()
            }
        })

        registerViewModel.errotoastUsername.observe(this, Observer { hasError->
            if(hasError==true){
                Toast.makeText(this, "UserName Already taken", Toast.LENGTH_SHORT).show()
                registerViewModel.donetoastUserName()
            }
        })
    }

    private fun navigateLogin() {
        Log.i("MYTAG", "insidisplayUsersList")
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }
}
