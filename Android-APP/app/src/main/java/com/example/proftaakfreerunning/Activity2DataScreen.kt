package com.example.proftaakfreerunning

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*
import android.widget.NumberPicker
import kotlinx.android.synthetic.main.activity_activity2_data_screen.*

class Activity2DataScreen : AppCompatActivity() {

    private var lv: ListView? = null
    private var np: NumberPicker? = null
    private val list = ArrayList<String>()
    private var nameLogInScreen = "No known name"
    private var IDDruppel = "No known ID"
    var getCheckpoint1 = "Checkpoint 1 is not used"
    var getCheckpoint2 = "Checkpoint 2 is not used"
    var getCheckpoint3 = "Checkpoint 3 is not used"
    var getCheckpoint4 = "Checkpoint 4 is not used"
    var getCheckpoint5 = "Checkpoint 5 is not used"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2_data_screen)

        nameLogInScreen = this.openFileInput("myName").bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some$text"
            }
        }

        IDDruppel = this.openFileInput("MyID").bufferedReader().useLines { lines ->
            lines.fold("") { some, text ->
                "$some$text"
            }
        }

        val helloTextView: TextView = findViewById(R.id.textView5)
        helloTextView.setText(nameLogInScreen)

        lv = findViewById(R.id.lvSectorTimes)

        np = findViewById(R.id.numberPicker)

        np?.setMinValue(1)
        np?.setMaxValue(5)

        np?.setOnValueChangedListener(onValueChangeListener)
}

    var onValueChangeListener: NumberPicker.OnValueChangeListener =
        NumberPicker.OnValueChangeListener { numberPicker, i, i1 ->
            Toast.makeText(
                this@Activity2DataScreen,
                "selected number " + numberPicker.value, Toast.LENGTH_SHORT
            )
        }

    fun test (view: View)
    {
        getCheckpoint1()

        val handler = Handler()
        handler.postDelayed({

            list.add(getCheckpoint1)
            list.add(getCheckpoint2)
            list.add(getCheckpoint3)
            list.add(getCheckpoint4)
            list.add(getCheckpoint5)

            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

            lv?.adapter = adapter
        }, 400)

        list.clear()
    }

    private fun getCheckpoint1() {

        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val myRef1 : DatabaseReference = database.child("users")
        myRef1.orderByChild(IDDruppel).equalTo(nameLogInScreen)

        // Read from the database
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {

                    getCheckpoint1 = dataSnapshot.child(IDDruppel).child("Checkpoint1").getValue(String::class.java)
                        .toString()
                    getCheckpoint2 = dataSnapshot.child(IDDruppel).child("Checkpoint2").getValue(String::class.java)
                        .toString()
                    getCheckpoint3 = dataSnapshot.child(IDDruppel).child("Checkpoint3").getValue(String::class.java)
                        .toString()
                    getCheckpoint4 = dataSnapshot.child(IDDruppel).child("Checkpoint4").getValue(String::class.java)
                        .toString()
                    getCheckpoint5 = dataSnapshot.child(IDDruppel).child("Checkpoint5").getValue(String::class.java)
                        .toString()

                    if (getCheckpoint1 == "null")
                    {
                        getCheckpoint1 = "Checkpoint 1 is not used"
                    }
                    if (getCheckpoint2 == "null")
                    {
                        getCheckpoint2 = "Checkpoint 2 is not used"
                    }
                    if (getCheckpoint3 == "null")
                    {
                        getCheckpoint3 = "Checkpoint 3 is not used"
                    }
                    if (getCheckpoint4 == "null")
                    {
                        getCheckpoint4 = "Checkpoint 4 is not used"
                    }
                    if (getCheckpoint5 == "null")
                    {
                        getCheckpoint5 = "Checkpoint 5 is not used"
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        }
        myRef1.addListenerForSingleValueEvent(valueEventListener)
    }


    fun setAmountOfButtons(view: View)
    {
        var amountOfButtons: Int = numberPicker.value

        val database = FirebaseDatabase.getInstance()

        database.getReference("Setup").child("NrButtons").setValue(amountOfButtons)
        Toast.makeText(applicationContext, amountOfButtons.toString(), Toast.LENGTH_SHORT).show()
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
