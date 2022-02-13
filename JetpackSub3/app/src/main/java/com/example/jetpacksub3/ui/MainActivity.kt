package com.example.jetpacksub3.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import com.example.jetpacksub3.R
import com.example.jetpacksub3.adapter.PagerAdapter
import com.example.jetpacksub3.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)
        val sectionsPagerAdapter = PagerAdapter(this, supportFragmentManager)
        activityMainBinding.viewPager.adapter = sectionsPagerAdapter
        activityMainBinding.tabs.setupWithViewPager(activityMainBinding.viewPager)
        activityMainBinding.topBar.setOnMenuItemClickListener { item ->
            if (item.itemId == R.id.favorite){
                val favoriteIntent = Intent(this, FavoriteActivity::class.java)
                startActivity(favoriteIntent)
                true
            }
            else false
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_bar, menu)
        return super.onCreateOptionsMenu(menu)
    }
}