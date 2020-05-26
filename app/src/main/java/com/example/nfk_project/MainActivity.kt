package com.example.nfk_project

import Graph
import NodeData
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.transition.TransitionManager
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_teacher_popup.*
import mDictionary
import okhttp3.*
import rNode
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList
import java.util.TreeMap as TreeMap


class MainActivity : AppCompatActivity() {
    var api: API = API()
    @RequiresApi(Build.VERSION_CODES.O)
    var suggestions = api.fetchStaffFromApi()

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

        //Tells the autocomplete to give suggestions when the user enters the second letter
        search_bar.threshold = 2
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)
        search_bar.setAdapter(adapter)

        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()

            if(api.allRooms.containsKey(selectedItem)){
                val intent = Intent(this, NavigationActivity::class.java)
                intent.putExtra("room", api.allRooms[selectedItem])
                startActivity(intent)
            }else{
                val teacher = api.allTeachers[selectedItem]
                if (teacher != null) {
                    createPopup(teacher)
                }
            }


        }
    }

    private fun createPopup(teacher : Teacher){
        // Initialize a new layout inflater instance
        val inflater:LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        // Inflate a custom view using layout inflater
        val view = inflater.inflate(R.layout.activity_teacher_popup,null)

        // Initialize a new instance of popup window
        val popupWindow = PopupWindow(
            view, // Custom view to show in popup window
            LinearLayout.LayoutParams.WRAP_CONTENT, // Width of popup window
            LinearLayout.LayoutParams.WRAP_CONTENT // Window height
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            popupWindow.elevation = 10.0F
        }else{
            println("not lolopop version")
        }

        val buttonPopup = view.findViewById<Button>(R.id.button_popup)
        val name = view.findViewById<TextView>(R.id.teacher_Name)
        val phone = view.findViewById<TextView>(R.id.teacher_Phone)
        val email = view.findViewById<TextView>(R.id.teacher_Email)
        val office = view.findViewById<TextView>(R.id.teacher_Office)
        val signature = view.findViewById<TextView>(R.id.teacher_Signature)
        val teacherPhoto = view.findViewById<ImageView>(R.id.teacher_Photo)

        name.text = teacher.Firstname.toString() + " " + teacher.Lastname.toString()
        phone.text = teacher.Mobile.toString()
        email.text = teacher.Mail.toString()
        office.text = teacher.RoomName.toString()
        signature.text = teacher.Signature.toString()


        if(teacher.Photo){
            val progressbar = view.findViewById<ProgressBar>(R.id.progressBar)
            progressbar.visibility = View.VISIBLE
            api.requestPhoto(teacher.Signature, fun (response : Bitmap?){
                runOnUiThread(){
                    progressbar.visibility = View.GONE
                    teacherPhoto.setImageBitmap(response)
                }
            })
        }else{

        }


        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
        }

        val buttonFindTeacher = view.findViewById<Button>(R.id.button_get_navigation)
        buttonFindTeacher.setOnClickListener{
            var intent = Intent(this, NavigationActivity::class.java)
            var room = Room(teacher.RoomName)
            intent.putExtra("room", room)
            startActivity(intent)
        }

        // Set a dismiss listener for popup window
        popupWindow.setOnDismissListener {
            Toast.makeText(applicationContext,"Popup closed",Toast.LENGTH_SHORT).show()
        }

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
            root_layout, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
    }


}





