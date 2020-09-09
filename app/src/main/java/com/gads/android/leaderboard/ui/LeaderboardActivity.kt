package com.gads.android.leaderboard.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import com.gads.android.leaderboard.R
import com.gads.android.leaderboard.adapter.LeaderboardAdapter
import com.gads.android.leaderboard.ui.learningleaders.LearningLeadersFragment
import com.gads.android.leaderboard.ui.skilliqleaders.SkillIQLeadersFragment
import com.gads.android.leaderboard.ui.submit.SubmitActivity
import kotlinx.android.synthetic.main.activity_leaderboard.*
import kotlinx.android.synthetic.main.leaderboard_toolbar.*

class LeaderboardActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_leaderboard)

        initToolbar()
        initViewPager()

        action_submit.setOnClickListener(this)
    }

    private fun initViewPager(){
        val fragments = listOf(
            LearningLeadersFragment(),
            SkillIQLeadersFragment()
        )
        val fragmentTitles = listOf("Learning Leaders", "Skill IQ Leaders")
        view_pager.adapter =
            LeaderboardAdapter(
                supportFragmentManager,
                fragments,
                fragmentTitles
            )
        tab_layout.setupWithViewPager(view_pager)
    }

    private fun initToolbar() {
        val toolbar = findViewById<Toolbar>(R.id.leaderboard_toolbar)
        setSupportActionBar(toolbar)
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.action_submit ->
                startActivity(Intent(this, SubmitActivity::class.java))
        }
    }
}
