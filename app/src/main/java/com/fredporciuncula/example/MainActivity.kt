package com.fredporciuncula.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.upstream.RawResourceDataSource

@OptIn(ExperimentalPagerApi::class)
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val sourceFactory = ProgressiveMediaSource.Factory(DefaultDataSource.Factory(this))
    val player = ExoPlayer.Builder(this).build().apply {
      val uri = RawResourceDataSource.buildRawResourceUri(R.raw.video)
      val mediaSource = sourceFactory.createMediaSource(MediaItem.Builder().setUri(uri).build())
      setMediaSource(mediaSource)
      repeatMode = Player.REPEAT_MODE_ONE
      playWhenReady = true
      prepare()
    }

    setContent {
      HorizontalPager(count = 2) { page ->
        when (page) {
          0 -> {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
            )
          }
          1 -> {
            Player(
              modifier = Modifier
                .fillMaxWidth(),
              player = player,
            )
          }
          else -> error("Invalid page")
        }
      }
    }
  }
}
