package studio.lunabee.amicrogallery.utils

import androidx.compose.ui.geometry.Offset
import kotlin.math.max
import kotlin.math.min

fun clampOffset(offset: Offset, pictureScale : Float, pictureWidth : Float, pictureHeight : Float) : Offset{
    val scale = max(0f,pictureScale - 1f) / 2
    return Offset(
        x = keepInInterval(offset.x, -scale*pictureWidth, scale*pictureWidth),
        y = keepInInterval(offset.y, -scale*pictureHeight, scale*pictureHeight)
    )
}

fun keepInInterval(x : Float, a : Float, b : Float) : Float{
    return max(a,min(x,b))
}

fun clampScale(scale: Float) : Float = max(1f, scale)

