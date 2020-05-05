package com.example.nfk_project

import android.app.DownloadManager
import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.system.Os.bind
import android.text.Editable
import android.text.TextWatcher
import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import android.util.Log
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.gson.GsonBuilder

import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException
import java.net.URL
import java.util.*
import kotlin.math.log
import java.util.Base64

class MainActivity : AppCompatActivity() {

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

        //todo Errors: cannot have spaces between words.
        val suggestions = resources.getStringArray(R.array.suggestions)

        //Tells the autocomplete to work from the users first letter, default is 2 letters.
        search_bar.threshold = 0

        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)

        search_bar.setAdapter(adapter)

        search_bar.setOnDismissListener { print("onDissmiss")}

        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
            //todo take user to correct navigation activity, room/person is stored in selectedItem
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("value", selectedItem)
            startActivity(intent)
        }

        fetchJson()
    }



    fun fetchJson(){

        println("Attempting to Fetch JSON")

        val url = "https://api.ju.se/api/Staff/ajoa"
        val password = "Km9Tacx9Dxae" + ":" + "TNDN15_Student"
        val data = password.toByteArray(charset("UTF-8"))
        val base64 = Base64.getEncoder().encodeToString(data)


        val request = Request.Builder().url(url).build()
        request.headers.set("Authorization", base64)


        val client = OkHttpClient()
        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()


                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, HomeFeed::class.java)

                println(homeFeed)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: " + e)
            }
        })
    }
}

private operator fun Headers.set(s: String, value: String) {

}

class Rooms(val roomNbr: String)



class HomeFeed(val videos: List<Video>)

class Video(val  id: Int, val name: String, val link: String, val imageUlr: String, val numberOfViews: Int, val channel: Channel)

class Channel(val name: String, val profileImageUrl: String)

