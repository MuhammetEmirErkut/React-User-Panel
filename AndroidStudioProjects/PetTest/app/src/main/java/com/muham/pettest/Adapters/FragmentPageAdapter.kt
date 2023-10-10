package com.muham.pettest

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.muham.pettest.Fragments.AccountFragment
import com.muham.pettest.Fragments.EnsyclopediaFragment
import com.muham.pettest.Fragments.ForumFragment
import com.muham.pettest.Fragments.HomeFragment

class FragmentPageAdapter (
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 0)
            HomeFragment()
        else if(position == 1)
            ForumFragment()
        else if(position == 2)
            EnsyclopediaFragment()
        else
            AccountFragment()
    }
}