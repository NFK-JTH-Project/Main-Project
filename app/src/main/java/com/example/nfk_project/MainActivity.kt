package com.example.nfk_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
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



        //todo: Can only fetch 42
        var suggestions = fetchStaffFromApi()


        //Tells the autocomplete to work from the users first letter
        search_bar.threshold = 2

        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)
        search_bar.setAdapter(adapter)
        search_bar.setOnDismissListener {print("onDissmiss")}



        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()

            //todo: selectedItem should retrive roomnbr
            val roomNumber: String = "function"

            // Display the clicked item using toast
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
            //todo take user to correct navigation activity, room/person is stored in selectedItem
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("value", selectedItem)
            startActivity(intent)
        }



    }

//Returns name of staff beggining with string userSearched
    fun fetchStaffFromApi(): MutableList<String> {

        //todo: Error handling for requests other than 200 OK

        println("Attempting to Fetch JSON")

        var list: MutableList<String> = ArrayList()

        //setting Authentication for API
        val password = "TNDN15_Student" + ":" + "Km9Tacx9Dxae"
        val data = password.toByteArray(charset("UTF-8"))
        val auth = Base64.getEncoder().encodeToString(data)


        //creating client
        val client = OkHttpClient().newBuilder().build()

        //Creating request to retrive all Staff from API
        val requestTeacher: Request = Request.Builder()
            .url("https://api.ju.se/api/Staff?filter=")
            .method("GET", null)
            .addHeader("Authorization", "Basic $auth")
            .build()

        //Creating request to retrive all Rooms from API
        val requestRooms: Request = Request.Builder()
            .url("https://api.ju.se/api/Room/Search?name=")
            .method("GET", null)
            .addHeader("Authorization", "Basic $auth")
            .build()

        //Handle response from retrieving all Staff, adding Staff by name to string array.
        client.newCall(requestTeacher).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                println("body $body")

                val gson = Gson()
                val listTeacherType = object : TypeToken<ArrayList<Teacher>>() {}.type
                var teachers = gson.fromJson<ArrayList<Teacher>>(body, listTeacherType)

                //teachers.forEachIndexed  { idx, tut -> println("> Item ${tut}") }
                teachers.forEach {
                    list.add(it.Firstname + " " + it.Lastname)

                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")

            }

        })
        //Handle response from retrieving all Rooms, adding Room by name to string array.
        client.newCall(requestRooms).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()

                println("body $body")

                val gson = Gson()
                val listRoomType = object : TypeToken<ArrayList<Room>>() {}.type
                var rooms = gson.fromJson<ArrayList<Room>>(body, listRoomType)

                //teachers.forEachIndexed  { idx, tut -> println("> Item ${tut}") }
                rooms.forEach {
                    list.add(it.Name)

                }

            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")

            }

        })
        return list

    //sökning mot servern


    }
}
//{"Signature":"Ajoa","Firstname":"Joakim","Lastname":"Andersson","Mobile":"","Mail":"joakim.andersson@ju.se","RoomName":"D1105","Photo":true}

class Staff(val teacher: List<Teacher>)

class Room(
    val Name: String,
    val Description: String,
    val More: String,
    val Types: String
)

class Teacher(
    val Signature: String,
    val Firstname: String,
    val Lastname: String,
    val Mobile: String,
    val Mail: String,
    val RoomName: String,
    val Photo: Boolean
)
{

    override fun toString() = "$Signature, $Firstname, $Lastname, $Mobile, $Mail, $RoomName, $Photo"
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
 */