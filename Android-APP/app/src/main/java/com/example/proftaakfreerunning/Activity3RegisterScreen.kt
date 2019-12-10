package com.example.proftaakfreerunning

import android.annotation.SuppressLint
import android.content.Intent
import android.nfc.NfcAdapter
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_activity3_register_screen.*
import kotlin.experimental.and

class Activity3RegisterScreen : AppCompatActivity() {

    var nfcForegroundUtil: NFCForegroundUtil? = null
    private var info: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity3_register_screen)

        info = findViewById<View>(R.id.tbDruppel) as TextView?
        nfcForegroundUtil = NFCForegroundUtil(this)
    }

    fun registerUser(view: View)
    {
        val database = FirebaseDatabase.getInstance()

//        database.getReference("users").setValue(tbNaam.text)
//        database.getReference("users").child(tbNaam.text.toString()).child("ID").setValue(tbDruppel.text)

        database.getReference("users").child(tbNaam.text.toString()).child("ID").setValue(tbDruppel.text.toString())
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
                Intent(Settings.ACTION_WIRELESS_SETTINGS)
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
