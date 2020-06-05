package com.example.nfk_project

import NodeData
import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.toolbar.view.*
import mDictionary
import kotlin.collections.ArrayList
import com.example.nfk_project.NaigationVisuals.navRepo
import com.example.nfk_project.NaigationVisuals.navCreator
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class NavigationActivity : AppCompatActivity() {
    private val TIME_OUT =  1000 * 60 * 20 // 20 minutes
    private val TOAST_MESSAGE = "Returning home due to inactivity"
    private val RUNNABLE = Runnable {             val i = Intent(this, MainActivity::class.java)
        i.putExtra("inactivity_message", TOAST_MESSAGE)
        startActivity(i)
        finish() }
    private val ACTIVITY_HANDLER = Handler()
    val ENGLISH = "en"
    val SWEDISH = "sv"
    var SEARCH_ITEM = Room("")


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val dictionary = mDictionary()
        val graph: Graph = initGraph()
        dictionary.init(graph.getRoomNames())

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        val imageView = findViewById<ImageView>(R.id.Nav_Map_Image_View)
        val searchedRoom = intent.getSerializableExtra("room") as Room
        SEARCH_ITEM = searchedRoom
        val roomName = searchedRoom.Name
        val textPath = getPath(roomName, graph, dictionary)
        val firstLetter = roomName[0]



        initBtns()
        setLanguageBtn()

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

    private fun setImageView(firstLetter: Char, roomName: String, imageView: ImageView){
        if(firstLetter != 'E' && roomName != "A4422b"){
            imageView.setImageResource(getOuterBuildingDrawable(firstLetter))
        }
        else {
            val floorName = getFloorString(roomName)
            navRepo.createBitMap(resources, floorName, roomName, this)
            navCreator.createBitmap(navRepo.getFloor(floorName)) { bitmap, _, _ ->
                imageView.setImageBitmap(bitmap) }
        }
    }

    private fun getFloorString(room: String): String{
        return when(room[1]){
            '1' -> "floor1"
            '2' -> "floor2"
            '3' -> "floor3"
            '4' -> "floor4"
            else -> "floor1"
        }
    }

    private fun getOuterBuildingDrawable(firstLetter: Char) : Int{
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

    private fun getPath(searchedFor: String, graph: Graph, dictionary: mDictionary): String{
        when(val destination = dictionary.getNameOfNode(searchedFor)){
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
                println("searched for: $destination")
                return graph.getPath("A", destination)
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setNavigationText(roomName: String, navigation: String){
        roomTitle.text = "${resources.getString(R.string.navigation_to)} $roomName"
        textView.text = navigation
    }
    

    private fun initGraph(): Graph {
        var directionString: String
        var positionString: String
        var connectionString: String

        if(langIsEnglish()){
            directionString = NodeData().directionNodes
            positionString = NodeData().positionNodes
            connectionString = NodeData().connections
        }else {
            directionString = NodeData_SE().directionNodes
            positionString = NodeData_SE().positionNodes
            connectionString = NodeData_SE().connections
        }
        
        val graph = Graph()
        val directionNodes: ArrayList<String> = directionString.split("\n") as ArrayList<String>
        val positionNodes: ArrayList<String> = positionString.split("\n") as ArrayList<String>
        val connections: ArrayList<String> = connectionString.split("\n") as ArrayList<String>
        for(node in directionNodes){
            val nodeName = node.split(";")[0]
            val nodeDirs: String = node.split(";")[1]
            val newNode = rNode(nodeName, nodeDirs)
            graph.addRoom(newNode)
        }
        for(connection in connections){
            val names = connection.split(" ")
            for(i in 1 until names.size){
                val root = graph.getRoom(names.get(0))
                val neighbor = graph.getRoom(names.get(i))
                if(root!=null && neighbor != null){
                    graph.setConnectionBetweenRooms(root, neighbor)
                }
            }
        }
        for(node in positionNodes){
            val vals = node.split(";")
            val parentName = vals[0]
            val childName = vals[1]
            val position = vals[2]
            val newRoom = rNode(childName, position)
            val parentRoom = graph.getRoom(parentName)
            graph.addRoom(newRoom)
            if(parentRoom != null)
                graph.setConnectionBetweenRooms(parentRoom, newRoom)
        }
        return graph
    }

    fun langIsEnglish() : Boolean{
        return resources.configuration.locale.toString() == "en"
    }

    fun initBtns(){
        var lang = resources.configuration.locale.toString()
        if(lang == ENGLISH){
            britainBtn.alpha = 0.3F
            swedenBtn.alpha = 1F
        }
        else{
            swedenBtn.alpha = 0.3F
            britainBtn.alpha = 1F
        }
    }


    fun setLanguageBtn(){
        var currentLanguage = resources.configuration.locale.toString()
        println("locales: $currentLanguage")
        britainBtn.setOnClickListener {
            if(currentLanguage != ENGLISH) {
                britainBtn.alpha = 0.3F
                swedenBtn.alpha = 1F
                changeLanguage(ENGLISH)
            }
        }

        swedenBtn.setOnClickListener {
            if(currentLanguage != SWEDISH) {
                swedenBtn.alpha = 0.3F
                britainBtn.alpha = 1F
                changeLanguage(SWEDISH)
            }
        }
    }


    private fun changeLanguage(language: String) {
        val myLocale = Locale(language)
        val res: Resources = resources
        val dm: DisplayMetrics = res.displayMetrics
        val conf: Configuration = res.configuration
        conf.locale = myLocale
        res.updateConfiguration(conf, dm)
        println("so far so good")
        val refresh = Intent(this, NavigationActivity::class.java)
        refresh.putExtra("room", SEARCH_ITEM)
        finish()
        startActivity(refresh)

    }

}

