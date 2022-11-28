package com.example.pbl_sns_25

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_25.databinding.FragmentFriendsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage


class FriendsFragment : Fragment() {
    var auth: FirebaseAuth? = null
    val db: FirebaseFirestore = Firebase.firestore
    var storage: FirebaseStorage = Firebase.storage
    var uid: String? = null
    var i=0
    var fid:String?=null
    val friends= mutableListOf(Friends("예시 친구 이메일:\ntest@gmail.com"))
    val friendsAdapter=FriendsAdapter(this, friends)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentFriendsBinding.inflate(inflater, container, false)

        uid= Firebase.auth.currentUser?.uid.toString()

        db.collection("friends").get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["uid"].toString() == uid) {
                        addToList(doc["fid"].toString())
                    }
                }
            }

        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = friendsAdapter


        binding.button2.setOnClickListener {
            val userEmail = binding.editTextTextEmailAddress.text.toString()
            findFriend(userEmail)
        }

        return binding.root
    }

   fun findFriend(Email:String){
        db.collection("users").get().addOnSuccessListener {
            for(doc in it){
                if(doc["email"].toString()==Email) {
                    fid = doc["uid"].toString()
                    addFriend(fid.toString())
                }
                //else
                    //Toast.makeText(context,"해당 이메일이 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun addFriend(fid:String){
        val infoMap = hashMapOf(
            "uid" to uid,
            "fid" to fid
        )
        db.collection("friends").add(infoMap).addOnCompleteListener {
            Toast.makeText(context,"친구 추가 성공", Toast.LENGTH_SHORT).show()
            addToList(fid)
        }
    }


    fun addToList(fid:String){
        db.collection("users").get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["uid"].toString() == fid) {
                        friends.add(0, Friends(doc["email"].toString()))
                        friendsAdapter.notifyItemInserted(0)
                    }
                }
            }

    }

    override fun onDestroy() {
        System.out.println("onDestroy")
        super.onDestroy()
    }
}