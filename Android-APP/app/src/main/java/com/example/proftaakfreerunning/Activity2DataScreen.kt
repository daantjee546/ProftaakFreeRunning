package com.example.proftaakfreerunning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class Activity2DataScreen : AppCompatActivity() {

    var lv: ListView? = null
    var nameLogInScreen = "123"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2_data_screen)

        nameLogInScreen = this.openFileInput("myName").bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some$text"
            }
        }

        val helloTextView: TextView = findViewById(R.id.textView5)
        helloTextView.setText(nameLogInScreen)

        val list = ArrayList<String>()
        list.add("Sector 1 = 3 seconden")
        list.add("Sector 2 = 3 seconden")
        list.add("Sector 3 = 3 seconden")
        list.add("Sector 4 = 3 seconden")
        list.add("Sector 5 = 3 seconden")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        lv?.adapter = adapter
    }

    fun test (view: View)
    {
        Toast.makeText(this, MainActivity().NameToOtherActivity, Toast.LENGTH_SHORT).show()
    }

    fun afmelden(view: View)
    {
        val filename = "myfile"
        val fileContents = "false"
        this.openFileOutput(filename, Context.MODE_PRIVATE).use {
            it.write(fileContents.toByteArray())
        }
        startActivity(Intent(this, MainActivity::class.java))
    }

    // blocks the return button, you have to solve the problem
    override fun onBackPressed() {
        Toast.makeText(applicationContext, "Back press disabled!", Toast.LENGTH_SHORT).show()
    }

}
