package com.example.pbl_sns_25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class FriendsPageActivity : AppCompatActivity() {

    private var destinationEmail: String? = null
    private var uid: String? = null
    private var recyclerView: RecyclerView? = null
    val posts = mutableListOf(
        FriendsPost(
            "https://firebasestorage.googleapis.com/v0/b/sns-25.appspot.com/o/blueinsta_original.png?alt=media&token=e2979537-710e-4ccb-b751-793a5f99d82b",
            "Welcome to Instagram!!!\n게시물 업로드 예시입니다."
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends_page)
        val nameView = findViewById<TextView>(R.id.nameView)
        val emailView = findViewById<TextView>(R.id.emailView)

        destinationEmail = intent.getStringExtra("destinationEmail")
        recyclerView = findViewById(R.id.recyclerView)
        val postAdapter=FriendsPostAdapter(this, posts)

        db.collection("users").get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["email"].toString() == destinationEmail) {
                        uid = doc["uid"].toString()
                        nameView.text = doc["name"].toString()
                        emailView.text = "현재 사용중인 계정:\n" + doc["email"].toString()
                    }
                }
            }

        db.collection("posts").get()
            .addOnSuccessListener {
                for (doc in it) {
                    if (doc["uid"].toString() == uid) {
                        posts.add(0, FriendsPost(doc["picUrl"].toString(), doc["text"].toString()))
                        postAdapter.notifyItemInserted(0)
                    }
                }
            }
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = LinearLayoutManager(this@FriendsPageActivity)
        recyclerView?.adapter = postAdapter
    }
}

