
package com.example.youtubemusicapp.model

data class YouTubeResponse(
    val items: List<Item>
)

data class Item(
    val id: Id,
    val snippet: Snippet
)

data class Id(val videoId: String)

data class Snippet(
    val title: String,
    val thumbnails: Thumbnails
)

data class Thumbnails(val default: Thumbnail)
data class Thumbnail(val url: String)
