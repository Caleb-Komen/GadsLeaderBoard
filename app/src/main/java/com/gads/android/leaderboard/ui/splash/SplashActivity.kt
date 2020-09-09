package com.gads.android.leaderboard.ui.splash

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.gads.android.leaderboard.R
import com.gads.android.leaderboard.ui.LeaderboardActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        lifecycleScope.launch {
            delay(3000L)
            startActivity(Intent(this@SplashActivity, LeaderboardActivity::class.java))
            finish()
        }
    }
}
