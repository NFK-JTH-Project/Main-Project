package com.example.nfk_project

import android.app.Activity
import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.hardware.input.InputManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethod
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.nfk_project.MapCreator.bitmapRepository
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.android.synthetic.main.toolbar.view.*
import java.util.*


open class MainActivity : AppCompatActivity() {
    var api: API = API()

    @RequiresApi(Build.VERSION_CODES.O)
    var suggestions = api.fetchStaffFromApi()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        if(intent.hasExtra("inactivity_message")) {
            var inactivityMessage = intent.getStringExtra("inactivity_message")
            Toast.makeText(this, inactivityMessage, Toast.LENGTH_LONG).show()
            println("Toasted")
        }

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.backBtn.visibility = View.GONE


        britainBtn.setOnClickListener {
            britainBtn.alpha = 0.3F
            swedenBtn.alpha = 1F


        }
        swedenBtn.setOnClickListener {
            swedenBtn.alpha = 0.3F
            britainBtn.alpha = 1F

        }

        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_Floor_1, R.id.navigation_Floor_2, R.id.navigation_Floor_3, R.id.navigation_Floor_4
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Will listen for changes to current navigationItem, to set the bitmapRepository at the correct floor
        navController.addOnDestinationChangedListener { navCon, dest, _ ->
            when (dest.label) {
                resources.getString(R.string.title_Floor_1) -> {
                    bitmapRepository.setCurrentFloor(1)
                }
                resources.getString(R.string.title_Floor_2) -> {
                    bitmapRepository.setCurrentFloor(2)
                }
                resources.getString(R.string.title_Floor_3) -> {
                    bitmapRepository.setCurrentFloor(3)
                }
                resources.getString(R.string.title_Floor_4) -> {
                    bitmapRepository.setCurrentFloor(4)
                }
            }
        }

        val search_bar = findViewById<AutoCompleteTextView>(R.id.search_bar)

        //Tells the autocomplete to give suggestions when the user enters the second letter
        search_bar.threshold = 2
        val adapter = ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, suggestions)
        search_bar.setAdapter(adapter)



        //On clicklistener that triggers when a suggestion is clicked, should take user to navigation activity
        search_bar.onItemClickListener = AdapterView.OnItemClickListener {
                parent, view,position, id->
            val selectedItem = parent.getItemAtPosition(position).toString()
            println(api.allRooms[selectedItem])

            if(api.allRooms.containsKey(selectedItem)){
                val intent = Intent(this, NavigationActivity::class.java)
                intent.putExtra("room", api.allRooms[selectedItem])
                startActivity(intent)
            }else{
                val teacher = api.allTeachers[selectedItem]
                if (teacher != null) {
                    closeKeyBoard()
                    createPopup(teacher)
                }
            }
        }

    }

    //disable backbutton so you can't leave the application
    override fun onBackPressed() {
    }

    fun closeKeyBoard(){
        val inputManager:InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(currentFocus?.windowToken, InputMethodManager.SHOW_FORCED)
    }

    //Make the homebutton always go to main acitvity
    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        if (Intent.ACTION_MAIN == intent.action) {
        }
    }
    //Disable recent application button
    override fun onPause() {
        super.onPause()
        val activityManager = applicationContext
            .getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.moveTaskToFront(taskId, 0)
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
        var popUpState = true
        val buttonFindTeacher = view.findViewById<Button>(R.id.button_get_navigation)
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

        if(teacher.RoomName == "") {
            buttonFindTeacher.visibility = View.GONE
        }

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
            runOnUiThread(){
                teacherPhoto.setImageResource(R.drawable.no_image_available)
            }
        }

        // Set a click listener for popup's button widget
        buttonPopup.setOnClickListener{
            // Dismiss the popup window
            popupWindow.dismiss()
            popUpState = false
        }


        buttonFindTeacher.setOnClickListener{
            popUpState = false
            var intent = Intent(this, NavigationActivity::class.java)
            var room = Room(teacher.RoomName)
            intent.putExtra("room", room)
            startActivity(intent)
        }

        // Finally, show the popup window on app
        TransitionManager.beginDelayedTransition(root_layout)
        popupWindow.showAtLocation(
            root_layout, // Location to display popup window
            Gravity.CENTER, // Exact position of layout to display popup
            0, // X offset
            0 // Y offset
        )
        Handler().postDelayed({
            if (popUpState) {
                popupWindow.dismiss()
                popUpState = false
            }
        }, 2 * 60 * 1000.toLong())
    }



}





