package com.gads.android.leaderboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class LeaderboardAdapter(
    fm: FragmentManager,
    private val fragments: List<Fragment>,
    private val fragmentsTitle: List<String>
) : FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentsTitle[position]
    }
}