package com.example.pbl_sns_25

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_25.databinding.FragmentFriendsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FriendsFragment : Fragment() {
    var auth: FirebaseAuth? = null

    var uid: String? = null
    var i=0
    val friends= mutableListOf(Friends("email"))
    val friendsAdapter=FriendsAdapter(this, friends)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)

        uid= Firebase.auth.currentUser?.uid.toString()

        db.collection("users").get()
            .addOnSuccessListener {
                for(doc in it){
                    if(doc["uid"].toString()==uid) {
                        db.collection("users").document(doc["email"].toString()).collection("friends").get()
                            .addOnSuccessListener {
                                for(doc in it) {
                                    friends.add(
                                        0,
                                        Friends(doc["email"].toString())
                                    )
                                    friendsAdapter.notifyItemInserted(0)
                                }
                            }
                    }
                }
            }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = friendsAdapter

        binding.button2.setOnClickListener {
            val userEmail = binding.editTextTextEmailAddress.text.toString()
            val infoMap = hashMapOf(
                "email" to userEmail
            )
            db.collection("users").get()
                .addOnSuccessListener {
                    for(doc in it){
                        if(doc["uid"].toString()==uid) {
                           db.collection("users").document(doc["email"].toString()).collection("friends")
                               .add(infoMap)
                               .addOnSuccessListener {
                                   println("Success")
                               }
                        }
                    }
                }
        }

        return binding.root
    }

    override fun onDestroy() {
        System.out.println("onDestroy")
        super.onDestroy()
    }
}