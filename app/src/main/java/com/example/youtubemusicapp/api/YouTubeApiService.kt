
package com.example.youtubemusicapp.api

import com.example.youtubemusicapp.model.YouTubeResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search")
    fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("maxResults") maxResults: Int = 20,
        @Query("type") type: String = "video"
    ): Call<YouTubeResponse>
}
