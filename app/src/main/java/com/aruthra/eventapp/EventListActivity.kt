package com.aruthra.eventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.*

class EventListActivity : AppCompatActivity() {

    lateinit var listView: ListView
    lateinit var dbRef: DatabaseReference

    val eventList = ArrayList<String>()
    val eventIds = ArrayList<String>()   // 🔥 important

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_list)

        listView = findViewById(R.id.listView)
        dbRef = FirebaseDatabase.getInstance().getReference("events")

        dbRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                eventList.clear()
                eventIds.clear()

                for (data in snapshot.children) {

                    val id = data.key!!
                    val name = data.child("name").value.toString()
                    val date = data.child("date").value.toString()

                    eventList.add("$name - $date")
                    eventIds.add(id)
                }

                val adapter = ArrayAdapter(
                    this@EventListActivity,
                    android.R.layout.simple_list_item_1,
                    eventList
                )

                listView.adapter = adapter
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // 🔥 CLICK EVENT → OPEN DETAILS
        listView.setOnItemClickListener { _, _, position, _ ->

            val intent = Intent(this, EventDetailActivity::class.java)
            intent.putExtra("eventId", eventIds[position])
            startActivity(intent)
        }
    }
}