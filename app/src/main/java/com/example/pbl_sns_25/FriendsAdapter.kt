package com.example.pbl_sns_25

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pbl_sns_25.databinding.ItemFriendsBinding


data class Friends(val email: String)
class FriendsViewHolder(val binding: ItemFriendsBinding) : RecyclerView.ViewHolder(binding.root)
class FriendsAdapter(private val context: FriendsFragment, private val friends: MutableList<Friends>) : RecyclerView.Adapter<FriendsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemFriendsBinding.inflate(inflater, parent, false)
        return FriendsViewHolder(binding)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onBindViewHolder(holder: FriendsViewHolder, position: Int) {
        val post=friends[position]
        holder.binding.friendsEmail.text = post.email.toString()
            holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView?.context,FriendsPageActivity::class.java)
            intent.putExtra("destinationEmail", holder.binding.friendsEmail.text)
            ContextCompat.startActivity(holder.itemView.context,intent,null)
        }
    }
    override fun getItemCount(): Int {
        return friends.size
    }
}