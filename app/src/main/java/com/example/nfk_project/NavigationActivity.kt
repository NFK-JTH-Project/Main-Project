package com.example.nfk_project

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_navigation.*
import okhttp3.*
import java.io.IOException
import java.lang.Error


class NavigationActivity : AppCompatActivity() {
    var api: API = API()
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val textView = findViewById<TextView>(R.id.textView)




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
                    textView.text = "No Room found"
                }else{
                    textView.text = "Navigation to $roomNbr"
                }
            }
        }
    }

}
