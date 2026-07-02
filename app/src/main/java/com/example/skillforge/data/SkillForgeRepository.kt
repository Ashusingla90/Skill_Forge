package com.example.skillforge.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SkillForgeRepository {
    private val api = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SkillForgeApi::class.java)

    suspend fun getSkillForgeData(): SkillForgeResponse {
        return api.getData()
    }
}
