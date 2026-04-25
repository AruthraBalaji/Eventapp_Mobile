package com.aruthra.eventapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class EventDetailActivity : AppCompatActivity() {

    lateinit var dbRef: DatabaseReference
    lateinit var auth: FirebaseAuth

    lateinit var eventId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_detail)

        val nameText = findViewById<TextView>(R.id.eventName)
        val dateText = findViewById<TextView>(R.id.eventDate)
        val countText = findViewById<TextView>(R.id.attendanceCount)
        val btn = findViewById<Button>(R.id.markAttendanceBtn)

        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference("events")

        eventId = intent.getStringExtra("eventId")!!

        // 🔥 LOAD EVENT DATA
        dbRef.child(eventId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val name = snapshot.child("name").value.toString()
                val date = snapshot.child("date").value.toString()

                nameText.text = name
                dateText.text = date

                // attendance count
                val count = snapshot.child("attendees").childrenCount
                countText.text = "Attendees: $count"
            }

            override fun onCancelled(error: DatabaseError) {}
        })

        // 🔥 MARK ATTENDANCE
        btn.setOnClickListener {

            val userId = auth.currentUser?.uid ?: return@setOnClickListener

            dbRef.child(eventId)
                .child("attendees")
                .child(userId)
                .setValue(true)
                .addOnSuccessListener {
                    Toast.makeText(this, "Attendance Marked ✅", Toast.LENGTH_SHORT).show()
                }
        }
    }
}