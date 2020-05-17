package com.example.nfk_project

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_navigation.*
import kotlinx.android.synthetic.main.floor0_fragment.*
import okhttp3.*
import java.io.IOException



class NavigationActivity : AppCompatActivity() {
    var api: API = API()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val textView = findViewById<TextView>(R.id.textView)




        val bundle :Bundle ?=intent.extras
        if (bundle!=null){

            val roomNbr = bundle.getString("value")
            val teacher = intent.getSerializableExtra("teacher") as Teacher


            if(teacher.Photo){
                requestPhoto(teacher.Signature)

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

    //todo Move this function to API
    fun requestPhoto(signature :String){
        val client = OkHttpClient().newBuilder().build()


        val requestRooms: Request = Request.Builder()
            .url("https://api.ju.se/api/Staff/$signature/Photo?height=")
            .method("GET", null)
            .addHeader("Authorization", "Basic ${api.auth}")
            .build()

        client.newCall(requestRooms).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response){
                runOnUiThread(){
                    photo.setImageBitmap(BitmapFactory.decodeStream(response.body?.byteStream()))
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")
            }
        })
    }
}
