package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.ImageButton
import android.os.Bundle
import com.example.keepsafe.R
import android.content.Intent
import android.view.MenuItem
import android.view.View
import com.example.keepsafe.NotesActivity
import com.example.keepsafe.PasswordActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import com.example.keepsafe.LoginActivity

class UserActivity : AppCompatActivity() {
    private var notesButton: ImageButton? = null
    private var passwordButton: ImageButton? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)
        notesButton = findViewById(R.id.notes_button)
        passwordButton = findViewById(R.id.password_button)
        notesButton!!.setOnClickListener(View.OnClickListener {
            val notesIntent = Intent(this@UserActivity, NotesActivity::class.java)
            startActivity(notesIntent)
        })
        passwordButton!!.setOnClickListener(View.OnClickListener {
            val passwordIntent = Intent(this@UserActivity, PasswordActivity::class.java)
            startActivity(passwordIntent)
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        FirebaseAuth.getInstance().signOut()
        Toast.makeText(applicationContext, "Signed out", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this@UserActivity, LoginActivity::class.java))
        finish()
        return super.onOptionsItemSelected(item)
    }
}