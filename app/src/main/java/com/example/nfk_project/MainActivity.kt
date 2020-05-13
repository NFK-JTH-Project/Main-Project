package com.example.nfk_project

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.annotation.RequiresApi
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
import kotlin.collections.ArrayList
import java.util.TreeMap as TreeMap


class MainActivity : AppCompatActivity() {
    var api: API = API()

    @RequiresApi(Build.VERSION_CODES.O)
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
        val suggestions = api.fetchStaffFromApi()

        //Tells the autocomplete to give suggestions when the user enters the second letter
        search_bar.threshold = 2

        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)
        search_bar.setAdapter(adapter)
        search_bar.setOnDismissListener {print("onDissmiss")}

        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()

            val selectedRoom = api.searchItems.get(selectedItem)
            println(selectedRoom)
            // Display the clicked item using toast
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
            //todo take user to correct navigation activity, room/person is stored in selectedItem
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("value", selectedRoom)
            startActivity(intent)
        }
    }
}


