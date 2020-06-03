package com.example.nfk_project.MapCreator

/* *** Programmed by Rasmus Svanberg, 2020 *** */

import android.graphics.Bitmap
import com.example.nfk_project.Helpers.MapKeyword

data class ImageLayer (
    var bitmap: Bitmap,
    var isVisible: Boolean,
    var keyWord: MapKeyword?
) {
    override fun toString() = keyWord.toString()
}