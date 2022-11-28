package com.example.pbl_sns_25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.example.pbl_sns_25.databinding.ItemPostBinding
import com.example.pbl_sns_25.databinding.PostLayoutBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
data class HomePost(val email:String, val picUrl: String, val text:String)

class PostViewHolder(val binding: ItemPostBinding) : RecyclerView.ViewHolder(binding.root)

class CustomAdapter(private val context: HomeFragment, private val homeposts: MutableList<HomePost>) : RecyclerView.Adapter<PostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemPostBinding.inflate(inflater, parent, false)
        return PostViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val homepost = homeposts[position]
        holder.binding.friendname.text = homepost.email
        holder.binding.friendtext.text=homepost.text
        Glide.with(holder.itemView.context)
            .load(homepost.picUrl)
            .error(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .centerCrop()
            .into(holder.binding.imageView)

    }

    override fun getItemCount(): Int {
        return homeposts.size
    }
}