package com.example.nfk_project.MapCreator

/* *** Programmed by Rasmus Svanberg, 2020 *** */

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

val mapCreator = MapCreator()

class MapCreator {
    fun createBitmap(layers: MutableList<ImageLayer>, send:(bitmap: Bitmap, dstRect: Rect, canvas: Canvas) -> Unit) {
        val bitmap = layers[0].bitmap.copy(Bitmap.Config.ARGB_8888, true)

        val dstRect = Rect(0, 0, layers[0].bitmap.width, layers[0].bitmap.height)
        val canvas = Canvas(bitmap)

        for (layer in layers) {
            if (layer.isVisible) {
                canvas.drawBitmap(layer.bitmap, null, dstRect, null)
            }
        }

        send(bitmap, dstRect, canvas)
    }
}