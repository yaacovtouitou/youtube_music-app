package com.example.youtubemusicapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.youtubemusicapp.api.YouTubeApiService
import com.example.youtubemusicapp.model.VideoItem
import com.example.youtubemusicapp.model.YouTubeResponse
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private val apiKey = "AIzaSyCuo6l9tS7yz4vxuSGSLcsjmlTkrKMHtBw" // Remplacez par votre clé API valide

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchInput = findViewById<EditText>(R.id.searchInput)
        val searchButton = findViewById<Button>(R.id.searchButton)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)

        searchButton.setOnClickListener {
            val query = searchInput.text.toString()
            val retrofit = Retrofit.Builder()
                .baseUrl("https://www.googleapis.com/youtube/v3/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val service = retrofit.create(YouTubeApiService::class.java)
            val call = service.searchVideos(query = query, apiKey = apiKey)

            call.enqueue(object : Callback<YouTubeResponse> {
                override fun onResponse(call: Call<YouTubeResponse>, response: Response<YouTubeResponse>) {
                    if (response.isSuccessful) {
                        val videos = response.body()?.items?.map {
                            VideoItem(
                                it.id.videoId,
                                it.snippet.title,
                                it.snippet.thumbnails.default.url
                            )
                        } ?: emptyList()

                        recyclerView.adapter = VideoAdapter(videos) { video ->
                            val intent = Intent(this@MainActivity, PlayerActivity::class.java)
                            intent.putExtra("videoId", video.videoId)
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(this@MainActivity, "Erreur de réponse: ${response.message()}", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<YouTubeResponse>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Erreur : ${t.message}", Toast.LENGTH_LONG).show()
                    t.printStackTrace() // Afficher plus de détails pour déboguer
                }
            })
        }
    }
}
