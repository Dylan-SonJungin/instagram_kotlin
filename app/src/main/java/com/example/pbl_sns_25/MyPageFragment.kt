package com.example.pbl_sns_25

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pbl_sns_25.databinding.ActivityLoginBinding
import com.example.pbl_sns_25.databinding.FragmentMyPageBinding
import com.example.pbl_sns_25.databinding.PostLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage

class MyPageFragment : Fragment() {
    private lateinit var binding : FragmentMyPageBinding
    var userEmail:String?=null
    val db: FirebaseFirestore = Firebase.firestore
    val storage=Firebase.storage

    var userList= ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentMyPageBinding.inflate(inflater, container, false)

        userEmail = arguments?.getString("userEmail")
        binding.textView.text = userEmail

        db.collection("posts").get()
            .addOnSuccessListener {
                for(doc in it){
                    if (doc["userEmail"].toString() == userEmail) {
                        userList.add(doc["picUrl"] as String)
                        System.out.println(doc["picUrl"])
                    }
                }
            }

        userList.add("https://firebasestorage.googleapis.com/v0/b/sns-25.appspot.com/o/blueinsta_original.png?alt=media&token=e2979537-710e-4ccb-b751-793a5f99d82b")
        //Test 용


        binding.button.setOnClickListener {
            Firebase.auth.signOut()
            startActivity(
                Intent(activity, LoginActivity::class.java)
            )
        }

        val mAdapter = PostAdapter(this, userList)
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = GridLayoutManager(context, 3)

        return binding.root
    }
}

