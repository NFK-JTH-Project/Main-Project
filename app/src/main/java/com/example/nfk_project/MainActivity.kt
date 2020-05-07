package com.example.nfk_project

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import okhttp3.*
import java.io.IOException
import java.util.*


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
        val password = "TNDN15_Student" + ":" + "Km9Tacx9Dxae"
        val data = password.toByteArray(charset("UTF-8"))
        val auth = Base64.getEncoder().encodeToString(data)


        //val request = Request.Builder().url(url).build()
        //request.header("Authorization")

        val client = OkHttpClient().newBuilder().build()
        val request: Request = Request.Builder()
            .url("https://api.ju.se/api/Staff/ajoa")
            .method("GET", null)
            .addHeader("Authorization", "Basic $auth")
            .build()



        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                println("body $body")

                val jsonObject: JsonObject = JsonParser().parse(body).asJsonObject

                val signature = jsonObject.get("Signature").asString
                val firstname = jsonObject.get("Firstname").asString
                val lastname = jsonObject.get("Lastname").asString
                val mobile = jsonObject.get("Mobile").asString
                val mail = jsonObject.get("Mail").asString
                val roomName = jsonObject.get("RoomName").asString
                var photo = jsonObject.get("Photo").asBoolean


                println("before")

                val teacher = Teacher(signature, firstname, lastname, mobile, mail, roomName, photo)
                println("after")
                println("Teacher object: $teacher")



            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")
            }
        })


    }
}
//{"Signature":"Ajoa","Firstname":"Joakim","Lastname":"Andersson","Mobile":"","Mail":"joakim.andersson@ju.se","RoomName":"D1105","Photo":true}


class Teacher(val signature: String, val firstname: String, val lastname: String, val mobile: String, val mail: String, val roomName: String, val photo: Boolean){
    override fun toString() = "$signature, $firstname, $lastname, $mobile, $mail, $roomName, $photo"
}




/*

val gson = Gson()
val teacher = gson.fromJson(body, Teacher::class.java)
println("teacher: " + teacher

val jsonObject: JsonObject = JsonParser().parse(body).asJsonObject
println(jsonObject.get("Signature"))
val signature = jsonObject.get("Signature").asString
val firstname = jsonObject.get("Firstname").asString
val lastname = jsonObject.get("Lastname").asString
val mobile = jsonObject.get("Mobile").asString
val mail = jsonObject.get("Mail").asString
val roomName = jsonObject.get("roomName").asString
var photo = false


println("before")

val teacher = Teacher(signature, firstname, lastname, mobile, mail, roomName, photo)
println("after")
println("Teacher object: $teacher")
 */