package com.aruthra.eventapp

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class CreateEventActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_event)

        val name = findViewById<EditText>(R.id.eventName)
        val date = findViewById<EditText>(R.id.eventDate)
        val createBtn = findViewById<Button>(R.id.createBtn)

        val dbRef = FirebaseDatabase.getInstance().getReference("events")

        createBtn.setOnClickListener {

            val eventName = name.text.toString().trim()
            val eventDate = date.text.toString().trim()

            if (eventName.isEmpty() || eventDate.isEmpty()) {
                Toast.makeText(this, "Enter all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val id = dbRef.push().key!!

            val event = HashMap<String, String>()
            event["id"] = id
            event["name"] = eventName
            event["date"] = eventDate

            dbRef.child(id).setValue(event)
                .addOnSuccessListener {
                    Toast.makeText(this, "Event Created ✅", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
        }
    }
}