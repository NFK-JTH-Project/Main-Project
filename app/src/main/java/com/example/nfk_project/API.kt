package com.example.nfk_project

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import okhttp3.internal.notify
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
                    searchItems[it.Signature] = it.RoomName
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

                //Create hardcoded rooms like "Fagerhults Aulan"
                createRoomWithNames()

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

    //This function creates rooms that have specific names
    fun createRoomWithNames(){
        val fagerhultsAulan = Room("E1423")
        val gjuterisalen = Room("E1405")
        val storaEnso = Room("E1029")
        val leonardo = Room("E3105d") //????? osäker
        val gallileo = Room("A4422b")
        val daVinci = Room("E4106")
        val indigo = Room("A2116")
        val lewinsalen = Room("D2419")
        val solon = Room("A4301")
        val theano = Room("B2225")
        val forumhumanum = Room("Ge308")
        val juAulan = Room("He102")
        val jpAulan = Room("B1024")
        val kurtJAulan = Room("Gb306")
        val munksjoaulan = Room("B1033")
        val sparbanksaulan = Room("B1014")
        val tvetasalen = Room("Hc113")
        val vistasalen = Room("Hb116")
        val vastbosalen = Room("Hb220")
        val ostbosalen = Room("Hc218")

        list.add("Fagerhults Aulan")
        list.add("Gjuteri Salen")
        list.add("Stora Enso")
        list.add("Leonardo")
        list.add("Gallileo")
        list.add("Da Vinci")
        list.add("Indigo")
        list.add("Lewinsalen")
        list.add("Solon")
        list.add("Theano")
        list.add("Forum Humanum")
        list.add("JU Aulan")
        list.add("JP Aulan")
        list.add("Kurt Johansson-aulan")
        list.add("Munksjöaulan")
        list.add("Sparbanksaulan")
        list.add("Tvetasalen")
        list.add("Vistasalen")
        list.add("Västbosalen")
        list.add("Östbosalen")

        allRooms["Fagerhults Aulan"] = fagerhultsAulan
        allRooms["Gjuteri Salen"] = gjuterisalen
        allRooms["Stora Enso"] = storaEnso
        allRooms["Leonardo"] = leonardo
        allRooms["Gallileo"] = gallileo
        allRooms["Da Vinci"] = daVinci
        allRooms["Indigo"] = indigo
        allRooms["Lewinsalen"] = lewinsalen
        allRooms["Solon"] = solon
        allRooms["Theano"] = theano
        allRooms["Forum Humanum"] = forumhumanum
        allRooms["JU Aulan"] = juAulan
        allRooms["JP Aulan"] = jpAulan
        allRooms["Kurt Johansson-aulan"] = kurtJAulan
        allRooms["Munksjöaulan"] = munksjoaulan
        allRooms["Sparbanksaulan"] = sparbanksaulan
        allRooms["Tvetasalen"] = tvetasalen
        allRooms["Vistasalen"] = vistasalen
        allRooms["Västbosalen"] = vastbosalen
        allRooms["Östbosalen"] = ostbosalen


    }

}
