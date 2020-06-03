package com.example.nfk_project.MapCreator

/* *** Programmed by Rasmus Svanberg, 2020 *** */

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.nfk_project.Helpers.MapKeyword
import com.example.nfk_project.R

val bitmapRepository = BitmapRepository()

class BitmapRepository {

    private var currentFloor: Int = 0

    private val floor1 = mutableListOf<ImageLayer>()
    private val floor2 = mutableListOf<ImageLayer>()
    private val floor3 = mutableListOf<ImageLayer>()
    private val floor4 = mutableListOf<ImageLayer>()
    private val campus = mutableListOf<ImageLayer>()

    fun changeVisibilityByKeyword(key: MapKeyword, visible: Boolean) {
        when (currentFloor) {
            1 -> {
                floor1.find {
                    it.keyWord == key
                }?.run {
                    isVisible = visible
                }
            }
            2 -> {
                floor2.find {
                    it.keyWord == key
                }?.run {
                    isVisible = visible
                }
            }
            3 -> {
                floor3.find {
                    it.keyWord == key
                }?.run {
                    isVisible = visible
                }
            }
            4 -> {
                floor4.find {
                    it.keyWord == key
                }?.run {
                    isVisible = visible
                }
            }
            5 -> {
                campus.find {
                    it.keyWord == key
                }?.run {
                    isVisible = visible
                }
            }
        }
    }


    fun setCurrentFloor(floor: Int) {
        currentFloor = floor
    }

    fun init(res: Resources, finished:(Boolean) -> Unit) {

        currentFloor = 1

        /* *** *** *** *** *** FLOOR 1 *** *** *** *** *** */
        var bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_base)
        bitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true)

        var layer = ImageLayer(bitmap, true, MapKeyword.f1Base)

        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_sidemenu)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Menu)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_stairs_elevators)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Stairs)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_vending_machines)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Vending)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_wc)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Wc)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_entrances_exits)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Exits)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_rooms)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Rooms)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_you_are_here)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Youarehere)
        floor1.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor1_layer_top)
        layer = ImageLayer(bitmap, true, MapKeyword.f1Top)
        floor1.add(layer)
        /* *** *** *** *** *** *** *** *** *** *** *** *** */


        /* *** *** *** *** *** FLOOR 2 *** *** *** *** *** */
        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_base)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Base)
        floor2.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_wc)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Wc)
        floor2.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_stairs_elevators)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Stairs)
        floor2.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_sidemenu)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Menu)
        floor2.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_rooms)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Rooms)
        floor2.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor2_layer_top)
        layer = ImageLayer(bitmap, true, MapKeyword.f2Top)
        floor2.add(layer)
        /* *** *** *** *** *** *** *** *** *** *** *** *** */

        /* *** *** *** *** *** FLOOR 3 *** *** *** *** *** */
        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_base)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Base)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_stairs_elevators)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Stairs)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_exits)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Exits)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_wc)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Wc)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_vending_machines)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Vending)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_rooms)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Rooms)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_top)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Top)
        floor3.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor3_layer_sidemenu)
        layer = ImageLayer(bitmap, true, MapKeyword.f3Menu)
        floor3.add(layer)
        /* *** *** *** *** *** *** *** *** *** *** *** *** */

        /* *** *** *** *** *** FLOOR 4 *** *** *** *** *** */

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_base)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Base)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_wc)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Wc)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_stairs_elevators)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Stairs)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_exits)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Exits)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_sidemenu)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Menu)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_rooms)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Rooms)
        floor4.add(layer)

        bitmap = BitmapFactory.decodeResource(res, R.drawable.floor4_layer_top)
        layer = ImageLayer(bitmap, true, MapKeyword.f4Top)
        floor4.add(layer)

        /* *** *** *** *** *** CAMPUS *** *** *** *** *** */

        finished(true)
    }

    fun getFloor() : MutableList<ImageLayer>? {
        return when (currentFloor) {
            1 -> floor1
            2 -> floor2
            3 -> floor3
            4 -> floor4
            5 -> campus
            else -> return null
        }
    }
}