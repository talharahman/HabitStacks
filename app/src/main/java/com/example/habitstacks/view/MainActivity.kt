package com.example.habitstacks.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.habitstacks.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class MainActivity : AppCompatActivity() {

    // Firebase Instance Variables
    private val firebaseAuth: FirebaseAuth? = null
    private val firebaseUser: FirebaseUser? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)

        /*  if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, DashBoard.newInstance())
                    .commitNow();
        }*/
    }
}