package com.example.proftaakfreerunning

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private var blijfIngelogd = "false"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        blijfIngelogd = this.openFileInput("myfile").bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some\n$text"
            }
        }

        if (blijfIngelogd == "\ntrue")
        {
            startActivity(Intent(this@MainActivity, Activity2DataScreen::class.java))
        }
        else
        {
            blijfIngelogd = "\nfalse"
        }
    }

    fun buttonclickLogin(view: View)
    {
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")

        if (cbBlijfIngelogd.isChecked)
        {
            val filename = "myfile"
            val fileContents = "true"
            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
        }

        startActivity(Intent(this@MainActivity, Activity2DataScreen::class.java))
    }

    fun buttonclickRegister(view: View)
    {
        startActivity(Intent(this@MainActivity, Activity3RegisterScreen::class.java))
    }

}
