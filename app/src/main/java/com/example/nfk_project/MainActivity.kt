package com.example.nfk_project

import Graph
import NodeData
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import mDictionary
import okhttp3.*
import rNode
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import java.util.TreeMap as TreeMap


class MainActivity : AppCompatActivity() {
    var api: API = API()
    @RequiresApi(Build.VERSION_CODES.O)
    var suggestions = api.fetchStaffFromApi()
    var graph = initGraph()
    var dictionary = mDictionary()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_Floor_0, R.id.navigation_Floor_1, R.id.navigation_Floor_2, R.id.navigation_Floor_3
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val search_bar = findViewById<AutoCompleteTextView>(R.id.search_bar)

        //Tells the autocomplete to give suggestions when the user enters the second letter
        search_bar.threshold = 2
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)
        search_bar.setAdapter(adapter)



        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            val selectedRoom = api.searchItems[selectedItem]

            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("graph", graph)

            if(api.allRooms.containsKey(selectedItem)){
                intent.putExtra("room", api.allRooms[selectedItem])
            }else{
                intent.putExtra("teacher", api.allTeachers[selectedItem])
            }
            startActivity(intent)
        }
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





