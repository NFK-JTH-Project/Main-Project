package com.example.nfk_project

import Graph
import NodeData
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.toolbar.view.*
import mDictionary
import rNode
import java.lang.Error
import java.util.*


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

        var imageView = findViewById<ImageView>(R.id.Nav_Map_Image_View)


        ACTIVITY_HANDLER.postDelayed(RUNNABLE, TIME_OUT.toLong())


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            ACTIVITY_HANDLER.removeCallbacks(RUNNABLE)
            startActivity(intent)
        }

        var dictionary = mDictionary()
        var graph: Graph = initGraph()
        dictionary.init(graph.getRoomNames())

        var searchedRoom = intent.getSerializableExtra("room") as Room

        var floor = getFloor(searchedRoom.Name)
        imageView.setImageResource(floor)
        setNavigationText(searchedRoom.Name, getPath(searchedRoom.Name, graph, dictionary))

    }

    fun getFloor(floorName: String): Int{
        val floor1 = R.drawable.floor1_complete_map
        val floor2 = R.drawable.floor2_complete_map
        val floor3 = R.drawable.floor3_complete_map
        val floor4 = R.drawable.floor4_complete_map
        val floorX = R.drawable.ju_area
        var floorNr = 0
        println("Floorname: $floorName")

        if(floorName.get(0) != 'E'){
            return if(floorName == "A4422b")
                floor1
            else
                floorX
        }

        floorNr = try{
            Character.getNumericValue(floorName[1])

        } catch (e: Error){
            println("wierd floor nr in getFloor/Navigation")
            1
        }
        println("FloorNr = $floorNr")
        return when(floorNr){
            1 -> floor1
            2 -> floor2
            3 -> floor3
            4 -> floor4
            else -> {
                println("In else returning floor 1")
                floor1
            }
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
                        'A' -> "This room is in building A (principal office)"
                        'B' -> "This room is in building B, Jönköping Business School (JIBS)"
                        'C' -> "This room is in building C, Library"
                        'D' -> "This room is in building D, Students house"
                        'F' -> "This room is in building F"
                        'G' -> "This room is in building G, School of Health and Welfare"
                        'H' -> "This room is in building H, School of Education and Communication"
                        'J' -> "This room is in building J, Campus Arena"
                        else ->  "Not found"
                    }
                }
                return(graph.getPath("A", destination))
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

