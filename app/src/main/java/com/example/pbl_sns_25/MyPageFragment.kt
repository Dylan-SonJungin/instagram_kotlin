package com.example.pbl_sns_25

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_25.databinding.FragmentMyPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MyPageFragment : Fragment() {
    //private lateinit var binding : FragmentMyPageBinding

    var auth: FirebaseAuth? = null

    var uid: String? = null
    val posts= mutableListOf(Post("https://firebasestorage.googleapis.com/v0/b/sns-25.appspot.com/o/blueinsta_original.png?alt=media&token=e2979537-710e-4ccb-b751-793a5f99d82b",
        "Welcome to Instagram!!!\n게시물 업로드 예시입니다."))
    val postAdapter=PostAdapter(this, posts)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentMyPageBinding.inflate(inflater, container, false)
        //val binding= FragmentMyPageBinding.inflate(layoutInflater)

        uid=Firebase.auth.currentUser?.uid.toString()

        db.collection("users").get()
            .addOnSuccessListener {
                for(doc in it){
                    if(doc["uid"].toString()==uid) {
                        binding.nameView.text = doc["name"].toString()
                        binding.emailView.text = "현재 사용중인 계정:\n"+doc["email"].toString()
                    }
                }
            }

        db.collection("posts").get()
            .addOnSuccessListener {
                for(doc in it) {
                    if (doc["uid"].toString() == uid) {
                        posts.add(0, Post(doc["picUrl"].toString(), doc["text"].toString()))
                        postAdapter.notifyItemInserted(0)
                    }
                }
            }

        //binding.postInt.text= posts.size.toString()
        //val mAdapter = PostAdapter(this, userList)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.adapter = postAdapter


        binding.button.setOnClickListener {
            startActivity(
                Intent(activity, LoginActivity::class.java)
            )
            activity?.finish()
            auth?.signOut()
        }

        return binding.root
    }

    override fun onDestroy() {
        System.out.println("onDestroy")
        super.onDestroy()
    }

}

