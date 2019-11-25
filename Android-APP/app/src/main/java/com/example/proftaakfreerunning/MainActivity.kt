package com.example.proftaakfreerunning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonclickLogin(view: View)
    {
        startActivity(Intent(this@MainActivity, Activity2DataScreen::class.java))
    }

    fun buttonclickRegister(view: View)
    {
        startActivity(Intent(this@MainActivity, Activity3RegisterScreen::class.java))
    }

}
