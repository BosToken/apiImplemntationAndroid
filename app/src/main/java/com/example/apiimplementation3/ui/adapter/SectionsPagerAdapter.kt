package com.example.apiimplementation3.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.apiimplementation3.ui.fragment.FollowerFragment
import com.example.apiimplementation3.ui.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    private val follower = FollowerFragment()
    private val following = FollowingFragment()
    override fun createFragment(position: Int): Fragment {

        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = follower
            1 -> fragment = following
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 2
    }

    fun getUser(user : String){
        val bundle = Bundle()
        bundle.putString("user", user)
        follower.arguments = bundle
        following.arguments = bundle
    }
}
