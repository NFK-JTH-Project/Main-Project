package com.example.nfk_project

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_navigation.*
import mDictionary
import okhttp3.*
import java.io.IOException
import java.lang.Error
import Graph
import NodeData
import android.content.Intent
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.toolbar.view.*
import rNode
import java.util.*


class NavigationActivity : AppCompatActivity() {
    var api: API = API()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        toolbar.backBtn.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        var dictionary = mDictionary()
        var graph: Graph = initGraph()
        dictionary.init(graph.getRoomNames())

        var searchedRoom = intent.getSerializableExtra("room") as Room
        setNavigationText(searchedRoom.Name, getPath(searchedRoom.Name, graph, dictionary))

    }

    fun getPath(searchedFor: String, graph: Graph, dictionary: mDictionary): String{
        var destination = dictionary.getNameOfNode(searchedFor)
        when(destination){
            "NOT_FOUND" -> return("The room number was not recognized")
            "NO_ROOM" -> return("The teacher has no room registered")
            else -> {
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

