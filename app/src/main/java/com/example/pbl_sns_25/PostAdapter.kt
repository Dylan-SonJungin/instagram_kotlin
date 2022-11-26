package com.example.pbl_sns_25
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.TransformationUtils.centerCrop
import com.bumptech.glide.request.RequestOptions
import com.example.pbl_sns_25.databinding.PostLayoutBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


data class Post(val picUrl: String, val text: String)

class MyViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)

class PostAdapter(private val context: MyPageFragment, private val posts: MutableList<Post>) : RecyclerView.Adapter<MyViewHolder>() {
    //val uid=Firebase.auth.currentUser?.uid.toString()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostLayoutBinding.inflate(inflater, parent, false)
        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
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