package com.example.jetpacksub3.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.jetpacksub3.R
import com.example.jetpacksub3.ui.MainFragment

class PagerAdapter(private val context: Context, fm: FragmentManager)
    : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val TAB_TITLES = arrayOf(
        R.string.tab_text_1,
        R.string.tab_text_2
    )

    override fun getItem(position: Int): Fragment = MainFragment.newInstance(position + 1)
    override fun getPageTitle(position: Int): CharSequence? =  context.resources.getString(
        TAB_TITLES[position])
    override fun getCount(): Int = TAB_TITLES.size
}