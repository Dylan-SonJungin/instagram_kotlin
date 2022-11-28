package com.example.pbl_sns_25

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.constraintlayout.motion.widget.TransitionBuilder.validate
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.example.pbl_sns_25.databinding.FragmentMyPageBinding
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.zip.Inflater

class HomeFragment : Fragment() {
    var auth: FirebaseAuth? = null
    val db: FirebaseFirestore = Firebase.firestore
    var uid: String? = null
    var userEmail:String?=null
    val friendList= mutableListOf<String>()
    //val homeposts= mutableListOf<HomePost>()
    val homeposts= mutableListOf(HomePost("예시 게시물","https://firebasestorage.googleapis.com/v0/b/sns-25.appspot.com/o/%E1%84%89%E1%85%A1%E1%86%BC%E1%84%89%E1%85%A1%E1%86%BC%E1%84%87%E1%85%AE%E1%84%80%E1%85%B5.jpeg?alt=media&token=380a7055-aeb9-4426-8a38-3d9c5aafdaac",
        "친구 게시물 예시입니다.\n친구 추가를 하면 친구의 게시물이\n홈 화면에 뜨게 됩니다."))
    val customAdapter=CustomAdapter(this, homeposts)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding = FragmentHomeBinding.inflate(inflater, container, false)

        uid=Firebase.auth.currentUser?.uid.toString()

        db.collection("friends").get()
            .addOnSuccessListener {
                for(doc in it){
                    if(doc["uid"]==uid) {
                        //friendList.add(doc["fid"].toString())
                        addPost(doc["fid"].toString())
                        System.out.println("찾음    " + doc["fid"])

                    }
                }
            }


        binding.postRecyclerview.setHasFixedSize(true)
        binding.postRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.postRecyclerview.adapter = customAdapter

        return binding.root
    }

    fun addPost(uid:String){
        db.collection("posts").get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["uid"] == uid) {
                        homeposts.add(0, HomePost(doc["name"].toString(), doc["picUrl"].toString(), doc["text"].toString()))
                        customAdapter.notifyItemInserted(0)
                    }
                }
            }
    }

    override fun onDestroy() {
        System.out.println("onDestroy")
        super.onDestroy()
    }

}