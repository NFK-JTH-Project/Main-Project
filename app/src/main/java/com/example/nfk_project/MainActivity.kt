package com.example.nfk_project

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

import kotlinx.android.synthetic.main.activity_main.*
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

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, suggestions)
            search_bar.setAdapter(adapter)

        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            // Display the clicked item using toast
            Toast.makeText(applicationContext,"Selected : $selectedItem",Toast.LENGTH_SHORT).show()
            //todo take user to correct navigation activity, room/person is stored in selectedItem
            val intent = Intent(this, NavigationActivity::class.java)
            intent.putExtra("value", selectedItem)
            startActivity(intent)
        }


    }
}
