package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.view.MenuItem
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import java.util.ArrayList

class PasswordActivity : AppCompatActivity() {
    var passwordAdapter: PasswordAdapter? = null
    var passwordList: MutableList<Password?> = ArrayList()
    var no_password_view: TextView? = null
    var fab2: FloatingActionButton? = null
    var firebaseDatabase2: FirebaseDatabase? = null
    private var currUser: FirebaseUser? = null
    private var databaseReference2: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_passwords)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView2 = findViewById(R.id.recycler_view2)
        recyclerView2!!.setHasFixedSize(true)
        recyclerView2!!.setLayoutManager(
            LinearLayoutManager(
                applicationContext
            )
        )
        no_password_view = findViewById(R.id.no_passwords)
        fab2 = findViewById(R.id.fab2)
        fab2!!.setOnClickListener(View.OnClickListener {
            val generatePasswordIntent =
                Intent(this@PasswordActivity, GeneratePasswordActivity::class.java)
            startActivity(generatePasswordIntent)
        })
        passwordAdapter = PasswordAdapter(passwordList, this)
        recyclerView2!!.setAdapter(passwordAdapter)
        currUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase2 = FirebaseDatabase.getInstance()
        databaseReference2 = firebaseDatabase2!!.getReference("passwords")
        val uId = currUser!!.uid
        databaseReference2!!.child(uId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val newPassword = dataSnapshot.getValue(
                        Password::class.java
                    )
                    passwordList.add(newPassword)
                }
                passwordAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this@PasswordActivity, UserActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var recyclerView2: RecyclerView? = null
    }
}