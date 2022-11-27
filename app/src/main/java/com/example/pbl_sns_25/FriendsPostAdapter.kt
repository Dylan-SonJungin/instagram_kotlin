package com.example.pbl_sns_25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pbl_sns_25.databinding.PostLayoutBinding

data class FriendsPost(val picUrl: String, val text: String)

class FriendsPostViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)

class FriendsPostAdapter(private val context: FriendsPageActivity, private val posts: MutableList<FriendsPost>) : RecyclerView.Adapter<FriendsPostViewHolder>() {
    //val uid=Firebase.auth.currentUser?.uid.toString()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsPostViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostLayoutBinding.inflate(inflater, parent, false)
        return FriendsPostViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FriendsPostViewHolder, position: Int) {
        val post=posts[position]
        holder.binding.postText.text=post.text.toString()
        Glide.with(holder.itemView.context)
            .load(post.picUrl)
            .error(R.drawable.no_image)
            .fallback(R.drawable.no_image)
            .centerCrop()
            .into(holder.binding.postView)

    }
    override fun getItemCount(): Int {
        return posts.size
    }
}
