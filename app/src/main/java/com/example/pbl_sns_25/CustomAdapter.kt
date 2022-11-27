package com.example.pbl_sns_25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.example.pbl_sns_25.databinding.ItemPostBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage

class CustomAdapter(val postList: Array<HomeFragment.Post>) : RecyclerView.Adapter<CustomAdapter.viewHolder>(){

    //lateinit var storage: FirebaseStorage = Firebase.storage

    fun add(item: HomeFragment.Post){
        postList.plus(item)
        notifyDataSetChanged()
    }

    inner class viewHolder(private val binding:ItemPostBinding): RecyclerView.ViewHolder(binding.root){
        fun setContents(pos: Int){
            binding.textView.text = postList.get(pos).name
            //binding.imageView.
            binding.textView2.text = postList.get(pos).text
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): viewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemPostBinding.inflate(layoutInflater, viewGroup, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int){
        viewHolder.setContents(position)
    }

    override fun getItemCount(): Int {
        return postList.size
    }
}