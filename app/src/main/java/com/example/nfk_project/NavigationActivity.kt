package com.example.nfk_project

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {
    var api: API = API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val textView = findViewById<TextView>(R.id.textView)
        val imageView = findViewById<ImageView>(R.id.photo)



        val bundle :Bundle ?=intent.extras
        if (bundle!=null){

            val roomNbr = bundle.getString("value")
            val teacher = intent.getSerializableExtra("teacher") as Teacher


            if(teacher.Photo){
                println("Photo Exisist")

            }
            else{
                println("NO PHOTO")
            }
            if (roomNbr != null) {
                if(roomNbr.isEmpty()){
                    textView.setText("No Room found")
                }else{
                    textView.setText("Navigation to "+ roomNbr)
                }
            }
        }

    }
}


