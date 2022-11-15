package com.example.pbl_sns_25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.pbl_sns_25.databinding.ActivityMainBinding


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
            if (home != null) {
                fragTransaction.show(home)
            }
        }

        else if (tag == TAG_FRIENDS) {
            if (friends!=null){
                fragTransaction.show(friends)
            }
        }

        else if (tag == TAG_MY_PAGE){
            if (myPage != null){
                fragTransaction.show(myPage)
            }
        }

        else if (tag == TAG_UPLOAD){
            if (uploadPic != null){
                fragTransaction.show(uploadPic)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }

}