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
import java.util.*


class NavigationActivity : AppCompatActivity() {
    var api: API = API()

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        var dictionary = mDictionary()
        var graph: Graph = intent.getSerializableExtra("graph") as Graph
        dictionary.init(graph.getRoomNames())

        if(intent.hasExtra("teacher")) {
            var teacher = intent.getSerializableExtra("teacher") as Teacher
            setPhoto(teacher)
            setNavigationText(teacher.RoomName, getPath(teacher.RoomName, graph, dictionary))
        }
        else {
            var searchedRoom = intent.getSerializableExtra("room") as Room
            setNavigationText(searchedRoom.Name, getPath(searchedRoom.Name, graph, dictionary))
        }

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

    fun setPhoto(teacher: Teacher){
        if(teacher.Photo){
            api.requestPhoto(teacher.Signature, fun (response : Bitmap?){
                runOnUiThread(){
                    photo.setImageBitmap(response)
                }
            })
        }
    }

    fun setNavigationText(roomName: String, navigation: String){
        textView.setText("Navigation to $roomName" + "\n$navigation")
    }

}

/*
*

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){

            val roomNbr = bundle.getString("value")
            val teacher = intent.getSerializableExtra("teacher") as Teacher


            if(teacher.Photo){
                api.requestPhoto(teacher.Signature, fun (response : Bitmap?){
                    runOnUiThread(){
                        photo.setImageBitmap(response)
                    }
                })
            }
            else{
                println("NO PHOTO")
            }
            if (roomNbr != null) {
                if(roomNbr.isEmpty()){
                    textView.text = "No Room found for $teacher"
                }else{
                    textView.text = "${teacher.Firstname}\nNavigation to $roomNbr"
                }
            }
        }*/