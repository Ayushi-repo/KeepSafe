package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import com.example.keepsafe.NotesAdapter
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import android.os.Bundle
import com.example.keepsafe.R
import com.example.keepsafe.NotesActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import android.content.Intent
import android.view.MenuItem
import android.view.View
import com.example.keepsafe.AddNoteActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.example.keepsafe.UserActivity
import java.util.ArrayList

class NotesActivity : AppCompatActivity() {
    var notesAdapter: NotesAdapter? = null
    var noteList: MutableList<Note?> = ArrayList()
    var no_note_view: TextView? = null
    var fab: FloatingActionButton? = null
    var firebaseDatabase: FirebaseDatabase? = null
    private var currUser: FirebaseUser? = null
    private var databaseReference: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noteslist)
        val actionBar = supportActionBar
        actionBar!!.setDisplayHomeAsUpEnabled(true)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView!!.setHasFixedSize(true)
        recyclerView!!.setLayoutManager(LinearLayoutManager(applicationContext))
        no_note_view = findViewById(R.id.no_notes)
        fab = findViewById(R.id.fab)
        fab!!.setOnClickListener(View.OnClickListener {
            val addNoteIntent = Intent(this@NotesActivity, AddNoteActivity::class.java)
            startActivity(addNoteIntent)
        })
        notesAdapter = NotesAdapter(noteList, this)
        recyclerView!!.setAdapter(notesAdapter)
        currUser = FirebaseAuth.getInstance().currentUser
        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase!!.getReference("notes")
        val uId = currUser!!.uid
        //        String id = databaseReference.child(uId).push().getKey();
        databaseReference!!.child(uId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (dataSnapshot in snapshot.children) {
                    val newNote = dataSnapshot.getValue(
                        Note::class.java
                    )
                    //                Note newNote = snapshot.getValue(Note.class);
                    noteList.add(newNote)
                }
                notesAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        startActivity(Intent(this@NotesActivity, UserActivity::class.java))
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var recyclerView: RecyclerView? = null
    }
}