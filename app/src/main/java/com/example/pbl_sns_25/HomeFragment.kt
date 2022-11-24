package com.example.pbl_sns_25

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import java.util.*
import java.util.zip.Inflater


//var user: FirebaseUser? = FirebaseAuth.getInstance().currentUser
var user: String = "gcLSCBjllq0ggIK0XgcB"
//var user: String = FirebaseAuth.getInstance().currentUser
val db: FirebaseFirestore = Firebase.firestore
var storage: FirebaseStorage = Firebase.storage
val itemsCollectionRef = db.collection("users")


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    class Posts(val name: String, val text: String)
    var friendList = arrayOf<String>()
    var postList = arrayOf(
        Posts("테스트1", "테스트텍스트입니다"),
        Posts("테스트2", "테스트텍스트입니다")
    )

    // 현재 user id로 친구목록 불러오기
    fun getFriends(){
        itemsCollectionRef.document(user).collection("friends")
            .get()
            .addOnSuccessListener { result ->
                for(document in result){
                    friendList = friendList.plus((document["fid"] as String).trim())
                    Log.d("친구추가", (document["fid"] as String).trim())
                }
                print("친구목록: ") //그냥 실행시 친구목록에 추가되어있는데 debug로 돌려보면 addOnSuccessListener시작부터 끝까지 그냥 넘어간다.
                // 화면이 다 그려지고 난 뒤에 함수가 불리는거 같기도하고,,,
                println(Arrays.toString(friendList))
            }
    }

    //친구목록에서 가져온 fid로 각 친구의 post불러오는 함수
    fun getPosts(){
        getFriends()
        friendList.forEach {
            println(it)
            itemsCollectionRef.document(it).collection("posts")
                .get()
                .addOnSuccessListener { result ->
                    for(document in result){
                        println(it)
                        postList = postList.plus(Posts(it, document["text"] as String))
                        Log.d("게시글추가", document["text"] as String)
                    }
                    print("게시글목록:")
                    println(Arrays.toString(postList))
                }
        }
        //return
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //getPosts()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        getPosts()
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        _binding?.postRecyclerview?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        _binding?.postRecyclerview?.adapter = CustomAdapter(postList)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        print("친구목록2")
        println(Arrays.toString(friendList))
        print("게시글목록2:")
        println(Arrays.toString(postList))
        _binding = null
    }
}