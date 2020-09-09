package com.gads.android.leaderboard.model

import com.google.gson.annotations.SerializedName

data class Learner(
    @SerializedName("name")
    val name: String,
    @SerializedName("hours")
    val hours: Int?,
    @SerializedName("score")
    val score: Int?,
    @SerializedName("country")
    val country: String,
    @SerializedName("badgeUrl")
    val badgeUrl: String) {
}