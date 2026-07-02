package com.example.skillforge.data

import retrofit2.http.GET

interface SkillForgeApi {
    @GET("android-assesment/notes/refs/heads/main/data.json")
    suspend fun getData(): SkillForgeResponse
}
