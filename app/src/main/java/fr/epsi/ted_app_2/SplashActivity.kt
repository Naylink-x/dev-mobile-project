package fr.epsi.ted_app_2

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val sharedPreferences: SharedPreferences = getSharedPreferences("account", Context.MODE_PRIVATE)
        Handler(Looper.getMainLooper()).postDelayed(Runnable {

            if (sharedPreferences.contains("lastName")) {
                val newIntent=Intent(application, FragmentActivity::class.java) // to replace by HomeActivity
                startActivity(newIntent)
                finish()
            }
            else {
                val newIntent=Intent(application, MainActivity::class.java)
                startActivity(newIntent)
                finish()
            }

        },2000)
    }
}