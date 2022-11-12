package com.example.pbl_sns_25

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    //각각의 Fragment와 연결 (게시물 업로드, 마이페이지, 친구 목록)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
        val navController = navHostFragment.navController
        // CAUTION: findNavController(R.id.fragment) in onCreate will fail.

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNavigationView?.setupWithNavController(navController)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean { //메뉴 생성
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    /*override fun onOptionsItemSelected(item: MenuItem): Boolean { //메뉴 선택
        when(item.itemId){
            R.id.action_home->homeFragment().show() //홈 화면이 보이도록
            R.id.action_myPage->myPageFragment().show()//마이페이지
            R.id.action_friendList->friendListFragment.show()//친구 목록
            R.id.action_uploadPic->uploadPicFragment().show()//사진 업로션
        }
    }*/
}