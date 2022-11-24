package com.example.pbl_sns_25

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pbl_sns_25.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


private const val TAG_HOME = "home_fragment"
private const val TAG_FRIENDS = "friends_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"
private const val TAG_UPLOAD = "upload_fragment"

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (Firebase.auth.currentUser == null) {
            startActivity(
                Intent(this, LoginActivity::class.java)
            )
            finish()
        }

        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.friendListFragment -> setFragment(TAG_FRIENDS, FriendsFragment())
                R.id.myPageFragment-> setFragment(TAG_MY_PAGE, MyPageFragment())
                R.id.uploadPicFragment -> setFragment(TAG_UPLOAD,UploadFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        var userEmail:String?
        userEmail=intent.getStringExtra("userEmail")

        if (manager.findFragmentByTag(tag) == null){
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME)
        val friends = manager.findFragmentByTag(TAG_FRIENDS)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)
        val uploadPic = manager.findFragmentByTag(TAG_UPLOAD)

        if (home != null){
            fragTransaction.hide(home)
        }

        if (friends != null){
            fragTransaction.hide(friends)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (uploadPic != null){
            fragTransaction.hide(uploadPic)
        }

        if (tag == TAG_HOME) {
            val homeFragment= HomeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout,homeFragment )
                .commit()
        }

        else if (tag == TAG_FRIENDS) {
            val friendFragment= FriendsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout,friendFragment )
                .commit()
        }

        else if (tag == TAG_MY_PAGE){
            val userFragment = MyPageFragment()
           // val uid=Firebase.auth.currentUser?.uid.toString()
            val bundle = Bundle()
            bundle.putString("userEmail", userEmail)
            userFragment.arguments = bundle
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout, userFragment)
                .commit()
        }

        else if (tag == TAG_UPLOAD){
            val uploadFragment= UploadFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.mainFrameLayout,uploadFragment )
                .commit()
        }

        fragTransaction.commitAllowingStateLoss()
    }

}