package com.example.proftaakfreerunning

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.content.Intent
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Build.ID
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Boolean.FALSE
import java.lang.Boolean.TRUE
import kotlin.experimental.and

private val TAG = MainActivity::class.java.simpleName

class MainActivity : AppCompatActivity() {

    private var blijfIngelogd = "false"
    var boolLogInISCorrect: Boolean = FALSE
    var nfcForegroundUtil: NFCForegroundUtil? = null

    private var info: TextView? = null

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
        info = findViewById<View>(R.id.tbIdDruppel) as TextView?
        nfcForegroundUtil = NFCForegroundUtil(this)
    }

    val valueEventListener = object : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {

            for (ds in dataSnapshot.children) {
                val keyName = ds.key
                val ID = ds.child("ID").getValue(String::class.java)

                if(dataSnapshot.child(tbIdDruppel.text.toString()).exists())
                {
                    boolLogInISCorrect = TRUE
                    Toast.makeText(applicationContext, "Already exists!!", Toast.LENGTH_SHORT).show()
                }
//                    else if (!boolLogInISCorrect)
//                    {
//                        Toast.makeText(applicationContext, "Does not exist!!", Toast.LENGTH_SHORT).show()
//                        boolLogInISCorrect = FALSE
//                    }
            }
        }

        override fun onCancelled(error: DatabaseError) {
            // Failed to read value
            Log.w(TAG, "Failed to read value.", error.toException())
        }
    }

    fun buttonclickLogin(view: View)
    {
        checkBlijfIngelogd()
        checkIfLogInIsCorrect()
    }

    fun logInSuccessful()
    {
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

    fun checkIfLogInIsCorrect() {

        val database: DatabaseReference = FirebaseDatabase.getInstance().getReference()
        val myRef1 : DatabaseReference = database.child("users")
        myRef1.orderByChild("Name").equalTo(tbIdNaam.text.toString())

        // Read from the database
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (ds in dataSnapshot.children) {
                    val keyName = ds.key
                    val ID = ds.child("ID").getValue(String::class.java)

                    if(dataSnapshot.child(tbIdDruppel.text.toString()).exists())
                    {
                        boolLogInISCorrect = TRUE
                        logInSuccessful()
                    }
                    else if (!boolLogInISCorrect)
                    {
                        Toast.makeText(applicationContext, "ERROR LOGIN!!", Toast.LENGTH_SHORT).show()
                        boolLogInISCorrect = FALSE
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

    public override fun onPause() {
        super.onPause()
        nfcForegroundUtil?.disableForeground()
    }

    public override fun onResume() {
        super.onResume()
        nfcForegroundUtil?.enableForeground()

        if (!nfcForegroundUtil?.getNfc()?.isEnabled()!!) {
            Toast.makeText(
                applicationContext,
                "Please activate NFC and press Back to return to the application!",
                Toast.LENGTH_LONG
            ).show()
            startActivity(
                Intent(android.provider.Settings.ACTION_HOME_SETTINGS)
//                Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
            )
        }
    }

    @SuppressLint("MissingSuperCall")
    public override fun onNewIntent(intent: Intent) {
        val tag = intent.getParcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)
        /*StringBuilder sb = new StringBuilder();
    for(int i = 0; i < tag.getId().length; i++){
        sb.append(new Integer(tag.getId()[i]) + " ");
    }*/
        info?.text = bytesToHex(tag!!.id)
        //byte[] id = tag.getId();
    }

    /**
     * Convenience method to convert a byte array to a hex string.
     *
     * @param  data  the byte[] to convert
     * @return String the converted byte[]
     */

    fun bytesToHex(data: ByteArray): String {
        val buf = StringBuffer()
        for (i in data.indices) {
            buf.append(byteToHex(data[i]).toUpperCase())
            buf.append(" ")
        }
        return buf.toString()
    }

    /**
     * method to convert a byte to a hex string.
     *
     * @param  data  the byte to convert
     * @return String the converted byte
     */
    fun byteToHex(data: Byte): String {
        val buf = StringBuffer()
        buf.append(toHexChar((data.toInt() ushr 4) and 0x0F))
//        buf.append(toHexChar(data and 0x0F))
        buf.append(toHexChar(data.and(0x0F).toInt()))
        return buf.toString()
    }

    /**
     * Convenience method to convert an int to a hex char.
     *
     * @param  i  the int to convert
     * @return char the converted char
     */
    fun toHexChar(i: Int): Char {
        return if (0 <= i && i <= 9) {
            ('0'.toInt() + i).toChar()
        } else {
            ('a'.toInt() + (i - 10)).toChar()
        }
    }
}
