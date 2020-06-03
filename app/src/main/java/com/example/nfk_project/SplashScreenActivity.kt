package com.example.nfk_project

/* *** Programmed by Rasmus Svanberg, 2020 *** */

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.nfk_project.MapCreator.bitmapRepository

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        Log.d("Splashscreen", "Loading...")

        Handler().postDelayed(Runnable { bitmapRepository.init(resources) { finished ->
                if (finished) {
                    Log.d("Splashscreen ", "DONE")
                    val intent = Intent(applicationContext, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }, 100)    //100 just to give the splashscreen a time to start,
                            // will still take as long as it need to before launching app.
    }
}