package com.example.nfk_project

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import java.util.*


class API {

    var searchItems = TreeMap<String, String>()

    var allTeachers = TreeMap<String, Teacher>()

    var allRooms = TreeMap<String, Room>()

    var list: MutableList<String> = ArrayList()

    var nameOfTeachers: MutableList<String> = ArrayList()

    //setting Authentication for API
    val password = "TNDN15_Student" + ":" + "Km9Tacx9Dxae"
    val data = password.toByteArray(charset("UTF-8"))
    @RequiresApi(Build.VERSION_CODES.O)
    val auth = Base64.getEncoder().encodeToString(data)

    //Returns name of staff beggining with string userSearched
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchStaffFromApi(): MutableList<String> {

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
                val gson = Gson()
                val listTeacherType = object : TypeToken<ArrayList<Teacher>>() {}.type
                var teachers = gson.fromJson<ArrayList<Teacher>>(body, listTeacherType)

                //teachers.forEachIndexed  { idx, tut -> println("> Item ${tut}") }
                teachers.forEach {
                    list.add(it.Firstname + " " + it.Lastname)
                    searchItems[it.Firstname + " " + it.Lastname] = it.RoomName
                    allTeachers[it.Firstname + " " + it.Lastname] = it
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

                val gson = Gson()
                val listRoomType = object : TypeToken<ArrayList<Room>>() {}.type
                var rooms = gson.fromJson<ArrayList<Room>>(body, listRoomType)

                //teachers.forEachIndexed  { idx, tut -> println("> Item ${tut}") }
                rooms.forEach {
                    list.add(it.Name)
                    searchItems[it.Name] = it.Name
                    allRooms[it.Name] = it
                }
            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")
            }
        })

        //This returns a list of strings containing all teachers name + lastname and all room numbers in Jönköping University.
        return list
    }

    //Return photo of teacher by signature
    fun requestPhoto(signature:String, callback: (photo : Bitmap?) -> Unit){

        val client = OkHttpClient().newBuilder().build()
        val requestRooms: Request = Request.Builder()
            .url("https://api.ju.se/api/Staff/$signature/Photo?height=")
            .method("GET", null)
            .addHeader("Authorization", "Basic $auth")
            .build()

        client.newCall(requestRooms).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response){
                val bitmap = BitmapFactory.decodeStream(response.body?.byteStream())
                callback.invoke(bitmap)
            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")
                callback.invoke(null)
            }
        })
    }



    fun fetchTeachers(callback: (MutableList<String>?) -> Unit){

        val client = OkHttpClient().newBuilder().build()
        val requestTeacher: Request = Request.Builder()
            .url("https://api.ju.se/api/Staff?filter=")
            .method("GET", null)
            .addHeader("Authorization", "Basic $auth")
            .build()


        client.newCall(requestTeacher).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response?.body?.string()
                val gson = Gson()
                val listTeacherType = object : TypeToken<ArrayList<Teacher>>() {}.type
                var teachers = gson.fromJson<ArrayList<Teacher>>(body, listTeacherType)

                teachers.forEach {
                    list.add(it.Firstname + " " + it.Lastname)
                    nameOfTeachers.add(it.Firstname + " " + it.Lastname)
                    searchItems[it.Firstname + " " + it.Lastname] = it.RoomName
                    allTeachers[it.Firstname + " " + it.Lastname] = it
                }

                callback(nameOfTeachers)

            }
            override fun onFailure(call: Call, e: IOException) {
                println("request failed: $e")
                callback(null)
            }
        })

    }
}
