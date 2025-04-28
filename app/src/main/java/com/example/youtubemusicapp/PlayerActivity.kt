package com.example.youtubemusicapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView

class PlayerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val playerView = YouTubePlayerView(this)
        setContentView(playerView)

        val videoId = intent.getStringExtra("videoId") ?: return

        lifecycle.addObserver(playerView)

        playerView.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(player: YouTubePlayer) {
                player.loadVideo(videoId, 0f)
            }
        })
    }
}
