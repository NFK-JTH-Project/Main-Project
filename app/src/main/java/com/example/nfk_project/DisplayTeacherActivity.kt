package com.example.nfk_project

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_diplay_teacher.*
import kotlinx.android.synthetic.main.activity_navigation.*

class DisplayTeacherActivity : AppCompatActivity() {
    val api = API()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_diplay_teacher)

        var teacher = intent.getSerializableExtra("teacher") as Teacher
        setPhoto(teacher)
        setTeacherDescription(teacher)

        FindTeacherBtn.setOnClickListener(View.OnClickListener {
            var intent = Intent(this, NavigationActivity::class.java)
            var room = Room(teacher.RoomName)
            intent.putExtra("room", room)
            startActivity(intent)
        })
    }


    fun setPhoto(teacher: Teacher){
        if(teacher.Photo){
            api.requestPhoto(teacher.Signature, fun (response : Bitmap?){
                runOnUiThread(){
                    photo.setImageBitmap(response)
                }
            })
        }
    }

    fun setTeacherDescription(teacher: Teacher){
        Name.text = teacher.Firstname.toString() + " " + teacher.Lastname.toString()
        Phone.text = teacher.Mobile.toString()
        Email.text = teacher.Mail.toString()
        Office.text = teacher.RoomName
        Signature.text = teacher.Signature
    }
}
