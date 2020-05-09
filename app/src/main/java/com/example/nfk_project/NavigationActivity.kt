package com.example.nfk_project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_navigation.*

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val textView = findViewById<TextView>(R.id.textView)

        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            val message = bundle.getString("value")
            if (message != null) {
                if(message.isEmpty()){
                    textView.setText("No Room found")
                }else{
                    textView.setText("Navigation to "+ message)
                }
            }

        }

        
    }
}
