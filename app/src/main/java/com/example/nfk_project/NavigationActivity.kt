package com.example.nfk_project

import Graph
import NodeData
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.nfk_project.MapCreator.ImageLayer
import com.example.nfk_project.MapCreator.MapCreator
import com.example.nfk_project.NaigationVisuals.NavigationRepository
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.toolbar.view.*
import mDictionary
import rNode
import java.lang.Error
import java.util.*
import kotlin.collections.ArrayList
import com.example.nfk_project.MapCreator.mapCreator
import com.example.nfk_project.NaigationVisuals.navRepo
import com.example.nfk_project.NaigationVisuals.navCreator


class NavigationActivity : AppCompatActivity() {
    var api: API = API()
    private val TIME_OUT =  1000 * 60 * 20 // 20 minutes
    private val TOAST_MESSAGE = "Returning home due to inactivity"
    private val RUNNABLE = Runnable {             val i = Intent(this, MainActivity::class.java)
        i.putExtra("inactivity_message", TOAST_MESSAGE)
        startActivity(i)
        finish() }
    private val ACTIVITY_HANDLER = Handler()


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        var dictionary = mDictionary()
        var graph: Graph = initGraph()

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        var imageView = findViewById<ImageView>(R.id.Nav_Map_Image_View)
        var searchedRoom = intent.getSerializableExtra("room") as Room
        var roomName = searchedRoom.Name
        var textPath = getPath(roomName, graph, dictionary)
        var firstLetter = roomName[0]


        dictionary.init(graph.getRoomNames())

        ACTIVITY_HANDLER.postDelayed(RUNNABLE, TIME_OUT.toLong())

        setSupportActionBar(toolbar)

        toolbar.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            ACTIVITY_HANDLER.removeCallbacks(RUNNABLE)
            startActivity(intent)
        }


        setNavigationText(roomName, textPath)
        setImageView(firstLetter, roomName, imageView)

    }

    fun setImageView(firstLetter: Char, roomName: String, imageView: ImageView){
        if(firstLetter != 'E' && roomName != "A4422b"){
            imageView.setImageResource(getOuterBuildingDrawable(firstLetter))
        }
        else {
            var floorName = getFloorString(roomName)
            navRepo.createBitMap(resources, floorName, roomName)
            navCreator.createBitmap(navRepo.getFloor(floorName)!!) { bitmap, _, _ ->
                imageView.setImageBitmap(bitmap) }
        }
    }

    fun getFloorString(room: String): String{
        var floorNr = room[1]
        when(floorNr){
            '1' -> return "floor1"
            '2' -> return "floor2"
            '3' -> return "floor3"
            '4' -> return "floor4"
            else -> return "floor1"
        }
    }

    fun getOuterBuildingDrawable(firstLetter: Char) : Int{
        return when(firstLetter){
            'A' -> R.drawable.highlight_a_comp
            'B' -> R.drawable.highlight_b_comp
            'C' -> R.drawable.highlight_c_comp
            'D' -> R.drawable.highlight_d_comp
            'F' -> R.drawable.highlight_f_comp
            'G' -> R.drawable.highlight_g_comp
            'H' -> R.drawable.highlight_h_comp
            'J' -> R.drawable.highlight_j_comp
            else -> R.drawable.highlight_all_comp
        }
    }

    override fun onBackPressed() {
        ACTIVITY_HANDLER.removeCallbacks(RUNNABLE)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onPause() {
        super.onPause()
        val activityManager = applicationContext
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(taskId, 0)
    }

    fun getPath(searchedFor: String, graph: Graph, dictionary: mDictionary): String{
        var destination = dictionary.getNameOfNode(searchedFor)
        when(destination){
            "NOT_FOUND" -> return("The room number was not recognized")
            "NO_ROOM" -> return("The teacher has no room registered")
            else -> {
                if(searchedFor[0] != 'E'){
                    println(searchedFor[0])
                    return when(searchedFor[0]){
                        'A' -> getString(R.string.building_a)
                        'B' -> getString(R.string.building_b)
                        'C' -> getString(R.string.building_c)
                        'D' -> getString(R.string.buidling_d)
                        'F' -> getString(R.string.buidling_f)
                        'G' -> getString(R.string.building_g)
                        'H' -> getString(R.string.building_h)
                        'J' -> getString(R.string.building_j)
                        else ->  getString(R.string.not_found)
                    }
                }
                var navText = graph.getPath("A", destination)
                return navText
            }
        }
    }

    fun setNavigationText(roomName: String, navigation: String){
        roomTitle.text = "${resources.getString(R.string.navigation_to)} $roomName"
        textView.text = "$navigation"
    }

    fun initGraph(): Graph{
        var data: NodeData = NodeData()
        var graph: Graph = Graph()
        var directionNodes: ArrayList<String> = data.directionNodes.split("\n") as ArrayList<String>
        var positionNodes: ArrayList<String> = data.positionNodes.split("\n") as ArrayList<String>
        var connections: ArrayList<String> = data.connections.split("\n") as ArrayList<String>
        for(node in directionNodes){
            var nodeName = node.split(";").get(0)
            var nodeDirs: String = node.split(";").get(1)
            var newNode = rNode(nodeName, nodeDirs)
            graph.addRoom(newNode)
        }
        for(connection in connections){
            var names = connection.split(" ")
            for(i in 1 until names.size){
                var root = graph.getRoom(names.get(0))
                var neighbor = graph.getRoom(names.get(i))
                if(root!=null && neighbor != null){
                    graph.setConnectionBetweenRooms(root, neighbor)
                }
            }
        }
        for(node in positionNodes){
            var vals = node.split(";")
            var parentName = vals.get(0)
            var childName = vals.get(1)
            var position = vals.get(2)
            var newRoom = rNode(childName, position)
            var parentRoom = graph.getRoom(parentName)
            graph.addRoom(newRoom)
            if(parentRoom != null)
                graph.setConnectionBetweenRooms(parentRoom, newRoom)
        }
        return graph
    }
}

