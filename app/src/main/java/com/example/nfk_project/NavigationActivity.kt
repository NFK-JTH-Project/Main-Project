package com.example.nfk_project

import Graph
import NodeData
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.toolbar.view.*
import mDictionary
import rNode
import java.util.*


class NavigationActivity : AppCompatActivity() {
    var api: API = API()
    private val TIME_OUT =  1000 * 60 * 2 // 2 minutes
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
        setNavigationText(searchedRoom.Name, getPath(searchedRoom.Name, graph, dictionary))






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
                if(destination[0] != 'E'){
                    println(destination[0])
                    return "Not found"
                }
                return(graph.getPath("A", destination))
            }
        }
    }

    fun setNavigationText(roomName: String, navigation: String){
        textView.setText("Navigation to $roomName" + "\n$navigation")
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

