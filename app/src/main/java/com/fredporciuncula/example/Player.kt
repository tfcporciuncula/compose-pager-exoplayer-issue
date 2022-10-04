package com.fredporciuncula.example

import android.content.Context
import android.view.LayoutInflater
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import com.fredporciuncula.example.databinding.TexturePlayerBinding
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout
import com.google.android.exoplayer2.ui.StyledPlayerView

@Composable
fun Player(
  modifier: Modifier = Modifier,
  player: Player? = null,
  surfaceType: PlayerSurfaceType = PlayerSurfaceType.Surface,
  /* @AspectRatioFrameLayout.ResizeMode */
  resizeMode: Int = AspectRatioFrameLayout.RESIZE_MODE_ZOOM,
  update: (StyledPlayerView) -> Unit = NoOpUpdate,
) {
  AndroidView(
    factory = { createPlayerView(context = it, player = player, surfaceType = surfaceType, resizeMode = resizeMode) },
    modifier = modifier,
    update = update,
  )
}

enum class PlayerSurfaceType { Surface, Texture }

private fun createPlayerView(
  context: Context,
  player: Player?,
  surfaceType: PlayerSurfaceType,
  resizeMode: Int,
): StyledPlayerView {
  return when (surfaceType) {
    PlayerSurfaceType.Surface -> StyledPlayerView(context)
    PlayerSurfaceType.Texture -> TexturePlayerBinding.inflate(LayoutInflater.from(context)).root
  }.apply { setup(player, resizeMode) }
}

private fun StyledPlayerView.setup(player: Player?, resizeMode: Int) {
  useController = false
  setKeepContentOnPlayerReset(true)
  this.resizeMode = resizeMode
  this.player = player
}
