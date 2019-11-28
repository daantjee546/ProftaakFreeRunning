package com.example.proftaakfreerunning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_activity3_register_screen.*

class Activity3RegisterScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity3_register_screen)
    }

    fun registerUser(view: View)
    {
        val database = FirebaseDatabase.getInstance()

//        database.getReference("users").setValue(tbNaam.text)
//        database.getReference("users").child(tbNaam.text.toString()).child("ID").setValue(tbDruppel.text)

        database.getReference("users").child(tbNaam.text.toString()).child("ID").setValue(tbDruppel.text.toString())
    }
}
