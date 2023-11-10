package com.example.mobifillatask.activities

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.mobifillatask.R
import com.example.mobifillatask.databinding.ActivityLoginScreenBinding
import com.example.mobifillatask.models.LoginRequest
import com.example.mobifillatask.utils.ResourceApp
import com.example.mobifillatask.utils.SessionManager
import com.example.mobifillatask.viewmodels.MobifiliaProviderFactroy
import com.example.mobifillatask.viewmodels.MobifiliaRepository
import com.example.mobifillatask.viewmodels.MobifiliaViewModel
import com.example.mobifillatask.roomDb.UserDetail
import com.example.mobifillatask.roomDb.UserDetailDatabase
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginScreen : AppCompatActivity() {

    lateinit var binding: ActivityLoginScreenBinding

    lateinit var viewModel: MobifiliaViewModel
    lateinit var progressBar: ProgressDialog
    lateinit var sessionManager: SessionManager
    lateinit var database: UserDetailDatabase



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
        database = Room.databaseBuilder(applicationContext, UserDetailDatabase::class.java, "userDetailDb")
            .build()


        sessionManager = SessionManager(this)
        val mobifiliaRepository = MobifiliaRepository()

        val mobifiliaProviderFactroy = MobifiliaProviderFactroy(mobifiliaRepository)
        viewModel = ViewModelProvider(
            this,
            mobifiliaProviderFactroy
        ).get(MobifiliaViewModel::class.java)

        progressBar = ProgressDialog(this).apply {
            setCancelable(false)
            setMessage(resources.getString(R.string.please_wait))
        }



        binding.btnCancel.setOnClickListener {
            finish()
        }
        binding.btnLogin.setOnClickListener {
            if (loginValidation()) {
                val name = binding.etUsername.text.toString()
                val password = binding.etUserPassword.text.toString()
                val loginRequest = LoginRequest(

                    name,
                    password
                )
                viewModel.userLogin(loginRequest, this, progressBar)

                val contact = UserDetail(0, name, password)

                // Insert the Contact into the database using a coroutine
                GlobalScope.launch(Dispatchers.IO) {
                    database.userDetailDao.insertUser(contact)
                }
            }
        }
        database.userDetailDao.getUSer().observe(this) { userDetails ->
            val allNames = StringBuilder()
            for (userDetail in userDetails) {
                allNames.append("${userDetail.name} - ${userDetail.password}\n")
                println("userdetails = ${allNames.toString()}")
            }
        }
        userLoginResponse()

    }

    private fun userLoginResponse() {
        viewModel.loginLive.observe(this, Observer { resposne ->
            when (resposne) {
                is ResourceApp.Success -> {
                    resposne.data?.let {


                        val userData = resposne.data.data

                        println("userdataisss $userData")

                        val token = userData?.token


                        val sharedPreferences = getSharedPreferences("LOGIN", MODE_PRIVATE)
                        val editor = sharedPreferences.edit()

                        editor.putBoolean("isLogin", true)

                        editor.apply()


                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                        finish()


                    }

                    progressBar.dismiss()

                }
                is ResourceApp.Error -> {
                    resposne.msg?.let {
                        Toast.makeText(this, R.string.userNotFount, Toast.LENGTH_SHORT).show()
                    }
                    progressBar.dismiss()
                }
                is ResourceApp.Loading -> {
                    progressBar.show()
                }
                else -> {}
            }
        })
    }

    private fun loginValidation(): Boolean {
        val email = binding.etUsername.text.toString()
        val password = binding.etUserPassword.text.toString()

        if (binding.etUsername.text.toString().isEmpty()) {
            binding.etUsername.error = getString(R.string.please_enter_email_id)
            binding.etUsername.requestFocus()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.etUsername.error = getString(R.string.invalid_email)
            binding.etUsername.requestFocus()
            return false
        } else if (password.isEmpty() && password.length < 4) {
            binding.etUserPassword.error = getString(R.string.plese_enter_password)
            binding.etUserPassword.requestFocus()
            return false

        } else if (password.length < 4) {
            binding.etUserPassword.error = getString(R.string.password_length_error)
            binding.etUserPassword.requestFocus()
            return false
        } else {
            return true
        }
    }


}