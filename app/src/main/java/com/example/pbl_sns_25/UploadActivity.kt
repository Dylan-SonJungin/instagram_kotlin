package com.example.pbl_sns_25

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.ContentUris
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import com.bumptech.glide.Glide
import com.example.pbl_sns_25.databinding.ActivityUploadBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.selects.select
import java.io.File
import java.lang.Long.getLong
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class UploadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadBinding
    var imageFile=File("")
    var text:String?=null
    var userEmail:String?=null
    val uid=Firebase.auth.currentUser?.uid.toString()
    var post = HashMap<String, String>()
    var imageUri:Uri?=null
    val db: FirebaseFirestore = Firebase.firestore
    var storage: FirebaseStorage = Firebase.storage

    companion object{
        const val REQ_GALLERY=1
    }
    private val imageResult=registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){result->
        if(result.resultCode==RESULT_OK){
            imageUri=result.data?.data
            imageUri?.let{
                imageFile= File(getRealPathFromURI(it))
                System.out.println("getRealPathFromURI(it)")
                Glide.with(this)
                    .load(imageUri)
                    .fitCenter()
                    .into(binding.imageView4)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.homebutton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
        binding.albumbutton.setOnClickListener {
            openGallery()
        }

        binding.upload.setOnClickListener{
            contentUpload()
        }
    }

    fun getRealPathFromURI(uri:Uri):String{
        var columnIndex=0
        val proj=arrayOf(MediaStore.Images.Media.DATA)
        val cursor=contentResolver.query(uri,proj,null,null,null)
        if(cursor!!.moveToFirst()){
            columnIndex=cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        }
        val result=cursor.getString(columnIndex)
        cursor.close()
        System.out.println("cursor.close()")
        return result
    }

    private fun openGallery(){
        val writePermission=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val readPermission=ContextCompat.checkSelfPermission(this,Manifest.permission.READ_EXTERNAL_STORAGE)
        if(writePermission==PackageManager.PERMISSION_DENIED||readPermission==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE),REQ_GALLERY)
        }
        else{
            val intent=Intent(Intent.ACTION_PICK)
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,"image/*")
            imageResult.launch(intent)
        }
    }
    fun contentUpload() {
        db.collection("users").get()
            .addOnSuccessListener {
                for(doc in it){
                    if(doc["uid"]==uid){
                        userEmail=doc["email"].toString()
                    }
                }
            }
        if (imageUri != null) {
            var fileName =
                SimpleDateFormat("yyyyMMddHHmmss").format(Date())
            var date=SimpleDateFormat("yyyy-MM-dd-HH-mm").format(Date())// 파일명이 겹치면 안되기 떄문에 시년월일분초 지정
            storage.getReference().child(uid).child(fileName)
                .putFile(imageUri!!)//어디에 업로드할지 지정
                .addOnSuccessListener { taskSnapshot -> // 업로드 정보를 담는다
                    taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                        var picUrl = it.toString()
                        post.put("uid", uid)
                        post.put("picUrl", picUrl);
                        post.put("text", binding.picText.text.toString())
                        post.put("name", userEmail.toString())
                        db.collection("posts").document().set(post)
                            .addOnSuccessListener {
                                Snackbar.make(
                                    binding.root,
                                    "Upload completed.",
                                    Snackbar.LENGTH_SHORT
                                ).show()
                                //finish()
                            }
                    }
                }
        }
    }
}