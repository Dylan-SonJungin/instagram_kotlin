package com.example.pbl_sns_25

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.example.pbl_sns_25.databinding.ItemPostBinding

class CustomAdapter(val postList: ArrayList<HomeFragment.Posts>) : RecyclerView.Adapter<CustomAdapter.viewHolder>(){
    inner class viewHolder(private val binding:ItemPostBinding): RecyclerView.ViewHolder(binding.root){
        fun setContents(pos: Int){
            binding.textView.text = postList.get(pos).name
            binding.textView2.text = postList.get(pos).text
        }
    }
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): CustomAdapter.viewHolder {
        val layoutInflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemPostBinding.inflate(layoutInflater, viewGroup, false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: viewHolder, position: Int){
        viewHolder.setContents(position)
        //viewHolder.textview.text
    }

    override fun getItemCount() = 10 //임의로 10개라고 정해둠
}