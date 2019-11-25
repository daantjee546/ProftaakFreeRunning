package com.example.proftaakfreerunning

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class Activity2DataScreen : AppCompatActivity() {

    var lv: ListView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_activity2_data_screen)

        lv = this.findViewById(R.id.lvSectorTimes)

        val list = ArrayList<String>()
        list.add("Sector 1 = 3 seconden")
        list.add("Sector 2 = 3 seconden")
        list.add("Sector 3 = 3 seconden")
        list.add("Sector 4 = 3 seconden")
        list.add("Sector 5 = 3 seconden")

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, list)

        lv?.adapter = adapter
    }
}
