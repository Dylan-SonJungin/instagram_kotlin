package com.example.pbl_sns_25
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.pbl_sns_25.databinding.PostLayoutBinding
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage


//class DataVo(val photo:String)

class MyViewHolder(val binding: PostLayoutBinding) : RecyclerView.ViewHolder(binding.root)

class PostAdapter(private val context: MyPageFragment, private val dataList: MutableList<String>) : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PostLayoutBinding.inflate(inflater, parent, false)

        return MyViewHolder(binding)
    }
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var imageview = holder.binding.imageView3
        Glide.with(holder.itemView.context)
            .load(dataList[position])
            .error(R.drawable.blueinsta)
            .apply(RequestOptions().centerCrop())
            .into(imageview)
        System.out.println("post adapter 값 :    "+dataList[position])
    }
    override fun getItemCount(): Int {
        return dataList.size
    }
}