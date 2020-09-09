package com.gads.android.leaderboard

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object LearnerHttpClient {
    fun getRetrofit(): Retrofit {
        val BASE_URL = "https://gadsapi.herokuapp.com"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getSubmitRetrofitService(): Retrofit{
        val BASE_URL = "https://docs.google.com/forms/d/e/"
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}