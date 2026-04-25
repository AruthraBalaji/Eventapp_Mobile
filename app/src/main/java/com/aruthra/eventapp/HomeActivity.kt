package com.aruthra.eventapp

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class HomeActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        auth = FirebaseAuth.getInstance()

        val welcomeText = findViewById<TextView>(R.id.welcomeText)
        val createEventBtn = findViewById<Button>(R.id.createEventBtn)
        val viewEventsBtn = findViewById<Button>(R.id.viewEventsBtn)
        val logoutBtn = findViewById<Button>(R.id.logoutBtn)

        val user = auth.currentUser
        welcomeText.text = "Welcome 👋\n${user?.email}"

        // ➕ Create Event
        createEventBtn.setOnClickListener {
            startActivity(Intent(this, CreateEventActivity::class.java))
        }

        // 📋 View Events
        viewEventsBtn.setOnClickListener {
            startActivity(Intent(this, EventListActivity::class.java))
        }

        // 🚪 Logout
        logoutBtn.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}