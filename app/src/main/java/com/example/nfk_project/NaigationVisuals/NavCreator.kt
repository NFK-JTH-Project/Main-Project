package com.example.nfk_project.NaigationVisuals

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Rect

val navCreator = NavCreator()

class NavCreator {
    fun createBitmap(layers: ArrayList<NavLayer>, send:(bitmap: Bitmap, dstRect: Rect, canvas: Canvas) -> Unit) {
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