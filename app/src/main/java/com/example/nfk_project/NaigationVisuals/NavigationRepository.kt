package com.example.nfk_project.NaigationVisuals


import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.nfk_project.R
import kotlin.math.floor

val navRepo = NavigationRepository()

class NavigationRepository {

    private val floor1 = ArrayList<NavLayer>()
    private val floor2 = ArrayList<NavLayer>()
    private val floor3 = ArrayList<NavLayer>()
    private val floor4 = ArrayList<NavLayer>()
    private val floorX = ArrayList<NavLayer>()



    fun createBitMap(res: Resources, floorname: String, endNode: String){
        resetLayers()
        var drawable = getDrawable(floorname)
        var bitmap = BitmapFactory.decodeResource(res, drawable)
        var layer = NavLayer(bitmap, true)
        addLayer(floorname, layer)

        drawable = getDrawable(endNode)
        if(drawable == -1) return
        println("this should not be printed")
        bitmap = BitmapFactory.decodeResource(res, drawable)
        layer = NavLayer(bitmap, true)
        addLayer(floorname, layer)


    }

    fun addLayer(floorname: String, layer: NavLayer){
        when(floorname){
            "floor1" -> floor1.add(layer)
            "floor2" -> floor2.add(layer)
            "floor3" -> floor3.add(layer)
            "floor4" -> floor4.add(layer)
            "floorX" -> floorX.add(layer)
        }
    }

    fun resetLayers(){
        floor1.clear()
        floor2.clear()
        floor3.clear()
        floor4.clear()
    }

    fun getDrawable(name: String): Int{
        return when(name){
            "floor1" -> R.drawable.floor1_complete_map
            "floor2" -> R.drawable.floor2_complete_map
            "floor3" -> R.drawable.floor3_complete_map
            "floor4" -> R.drawable.floor4_complete_map
            "E1017" -> R.drawable.e1017
            "E1028" -> R.drawable.e1028
            "E1022" -> R.drawable.e1022
            "highlight_all_comp" -> R.drawable.highlight_all_comp
            else -> -1
        }
    }

    fun getFloor(floorname: String): ArrayList<NavLayer>{
        return when (floorname) {
            "floor1" -> floor1
            "floor2" -> floor2
            "floor3" -> floor3
            "floor4" -> floor4
            else -> return floor1
        }
    }
}