package com.example.pbl_sns_25

import android.app.Activity
import android.content.ContentUris
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.registerForActivityResult
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pbl_sns_25.databinding.FragmentHomeBinding
import com.example.pbl_sns_25.databinding.FragmentUploadBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*


class UploadFragment : Fragment() {
    private var _binding: FragmentUploadBinding? = null
    private val binding get() = _binding!!

    val PICK_IMAGE_FROM_ALBUM = 0
    var photoUri: Uri? = null

    var storage: FirebaseStorage? = null
    var firestore: FirebaseFirestore? = null
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val firestore = FirebaseFirestore.getInstance()
        val auth = FirebaseAuth.getInstance()

        binding.button.setOnClickListener{
            postUpload()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE_FROM_ALBUM) {
            //이미지 선택시
            if(resultCode == Activity.RESULT_OK){
                //이미지뷰에 이미지 세팅
                println(data?.data)
                photoUri = data?.data
                binding.newImageView.setImageURI(data?.data)
            }
            else{
                //finish()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUploadBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun postUpload(){
        val imageRef = storage?.reference?.child("testfilename")
        val contentUri = ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, 1234)
        imageRef?.putFile(contentUri)?.addOnCompleteListener{
            if(it.isSuccessful){
                Snackbar.make(binding.root, "Upload completed.", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}