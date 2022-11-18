package com.example.pbl_sns_25

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_sns_25.databinding.ActivityLoginBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signIn.setOnClickListener {
            val userEmail = binding.userEmail.text.toString()
            val password = binding.password.text.toString()
            doLogin(userEmail, password)
        }

        binding.signUp.setOnClickListener{
            startActivity(
                Intent(this, SignupActivity::class.java))
            finish()
            //회원가입 페이지로 이동
        }
    }
    private fun doLogin(userEmail: String, password: String) {
        Firebase.auth.signInWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) { // it: Task<AuthResult!>
                if (it.isSuccessful) {
                    startActivity(
                        Intent(this, MainActivity::class.java))
                    finish ()
                } else {
                    Log.w("LoginActivity", "signInWithEmail", it.exception)
                    Toast.makeText(this, "Login Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}