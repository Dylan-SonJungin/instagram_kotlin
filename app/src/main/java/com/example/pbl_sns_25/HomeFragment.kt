package com.example.pbl_sns_25

import android.content.Context
import android.os.Bundle
import android.util.Log
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
import com.google.android.gms.tasks.Tasks.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.runBlocking
import java.util.*
import java.util.zip.Inflater


//var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser

class HomeFragment : Fragment() {
    var user: String = "gcLSCBjllq0ggIK0XgcB"
    val db: FirebaseFirestore = Firebase.firestore
    var storage: FirebaseStorage = Firebase.storage
    val itemsCollectionRef = db.collection("users")
    private lateinit var binding: FragmentHomeBinding

    class Post(val name: String, val text: String)
    var friendList = arrayOf<String>("a@gmail.com")
    var postList = arrayOf<Post>()
    val adapter = CustomAdapter(getPosts())



    // 현재 user id로 친구목록 불러오기
    private fun getFriends(){
        itemsCollectionRef.document(user).collection("friends")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    friendList = friendList.plus((document["fid"] as String).trim())
                    //Log.d("친구추가", (document["fid"] as String).trim())
                }
                //print("친구목록: ")
                //println(Arrays.toString(friendList))
            }
    }

    //친구목록에서 가져온 fid로 각 친구의 post불러오는 함수
    private fun getPosts(): Array<Post>{
        getFriends()
        friendList.forEach {
            println(it)
            itemsCollectionRef.document(it).collection("posts")
                .get()
                .addOnSuccessListener { result ->
                    for(document in result){
                        println(it)
                        postList = postList.plus(Post(it, document["text"] as String))
                        //Log.d("게시글추가", document["text"] as String)
                    }
                    //print("게시글목록:")
                    //println(Arrays.toString(postList))
                }
        }
        return postList
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getPosts()

        binding?.refreshLayout?.setOnRefreshListener {
            var post = Post("테스트3", "테스트텍스트3")
            adapter.add(post)
            println(Arrays.toString(postList))
            postList.plus(post)
            //Log.d("추가된 post", Arrays.toString(postList))
            //getPosts()
            //binding.postRecyclerview?.adapter = CustomAdapter(getPosts())
            //adapter.notifyDataSetChanged()
            binding?.refreshLayout?.isRefreshing = false
        }
        binding.postRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


    override fun onResume() {
        super.onResume()
        binding.postRecyclerview?.adapter = CustomAdapter(getPosts())
    }

    override fun onDestroy() {
        super.onDestroy()
        print("친구목록2")
        println(Arrays.toString(friendList))
        print("게시글목록2:")
        println(Arrays.toString(postList))
    }
}