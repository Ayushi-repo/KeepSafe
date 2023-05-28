package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseUser
import android.os.Bundle
import com.example.keepsafe.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import android.text.TextUtils
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.keepsafe.NotesActivity
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {
    var noteMsgText: EditText? = null
    var saveNoteButton: Button? = null
    var cancelAddNoteButton: Button? = null
    var noteMsg: String? = null
    var addNoteDate: String? = null
    private var databaseReference: DatabaseReference? = null
    private var currUser: FirebaseUser? = null
    private var uId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addnote)
        noteMsgText = findViewById(R.id.msgNote)
        databaseReference = FirebaseDatabase.getInstance().getReference("notes")
        currUser = FirebaseAuth.getInstance().currentUser
        uId = currUser!!.uid
        saveNoteButton = findViewById(R.id.saveNoteButton)
        saveNoteButton!!.setOnClickListener(View.OnClickListener { v -> addNote(v) })
        cancelAddNoteButton = findViewById(R.id.cancelAddNoteButton)
        cancelAddNoteButton!!.setOnClickListener(View.OnClickListener { cancelOperation() })
    }

    fun addNote(view: View?) {
        noteMsg = noteMsgText!!.text.toString()
        if (TextUtils.isEmpty(noteMsg)) return
        val calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("dd-MMM-yy, HH:mm")
        addNoteDate = simpleDateFormat.format(calendar.time)
        addNote(noteMsg!!, addNoteDate)
    }

    private fun addNote(noteMessage: String, noteDate: String?) {
        val id = databaseReference!!.child(uId!!).push().key
        val noteList = Note(id, noteMessage, noteDate)
        databaseReference!!.child(uId!!).child(id!!).setValue(noteList).addOnCompleteListener {
            Toast.makeText(this@AddNoteActivity, "Note added", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, NotesActivity::class.java))
        }
    }

    private fun cancelOperation() {
        startActivity(Intent(applicationContext, NotesActivity::class.java))
    }
}