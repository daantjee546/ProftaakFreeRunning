package com.example.proftaakfreerunning

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.os.Build.ID
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

private val TAG = MainActivity::class.java.simpleName

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
        checkBlijfIngelogd()



        startActivity(Intent(this@MainActivity, Activity2DataScreen::class.java))
    }

    fun buttonclickRegister(view: View)
    {
        startActivity(Intent(this@MainActivity, Activity3RegisterScreen::class.java))
    }

    private fun checkBlijfIngelogd()
    {
        if (cbBlijfIngelogd.isChecked)
        {
            val filename = "myfile"
            val fileContents = "true"
            this.openFileOutput(filename, Context.MODE_PRIVATE).use {
                it.write(fileContents.toByteArray())
            }
        }
    }

    fun checkIfLogInIsCorrect()
    {
        val database: DatabaseReference
        database = FirebaseDatabase.getInstance().getReference()

        val myRef1 : DatabaseReference = database.child("users")
        myRef1.orderByChild("ID").equalTo("555555")

        // Read from the database
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val keyName = ds.key
                    val ID = ds.child("ID").getValue(String::class.java)

                    if(dataSnapshot.child("ID").exists())
                    {
                        Toast.makeText(applicationContext, "Already exists!!", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(applicationContext, "Does not exist!!", Toast.LENGTH_SHORT).show()
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
}
