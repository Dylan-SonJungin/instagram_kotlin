package com.example.pbl_sns_25

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pbl_sns_25.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignupActivity : AppCompatActivity() {
   private lateinit var binding : ActivitySignupBinding
    val db: FirebaseFirestore = Firebase.firestore
    val itemsCollectionRef = db.collection("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val uid=Firebase.auth.currentUser?.uid.toString()

        binding.signUp.setOnClickListener {
            val userEmail = binding.userEmail.text.toString()
            val userName = binding.userName.text.toString()
            val password = binding.userPassword.text.toString()

            System.out.println("Signup UID:   "+uid)
            val infoMap = hashMapOf(
                "name" to userName,
                "email" to userEmail,
                "uid" to null
            )

            itemsCollectionRef.get().addOnSuccessListener {
                itemsCollectionRef.document(userEmail).set(infoMap)
                doSignUp(userEmail, password)
            }
        }
    }
    private fun doSignUp(userEmail: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(userEmail, password)
            .addOnCompleteListener(this) { // it: Task<AuthResult!>
                if (it.isSuccessful) {
                    val uid=Firebase.auth.currentUser?.uid.toString()
                    itemsCollectionRef.document(userEmail).update("uid",uid)
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish ()
                } else {
                    Toast.makeText(this, "Sign Up Failed.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}