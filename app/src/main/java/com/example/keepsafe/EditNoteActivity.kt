package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseUser
import android.os.Bundle
import com.example.keepsafe.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnCompleteListener
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.keepsafe.NotesActivity
import com.google.android.gms.tasks.OnSuccessListener

class EditNoteActivity : AppCompatActivity() {
    var noteEditText: EditText? = null
    var cancelButton: Button? = null
    var updateButton: Button? = null
    var deleteButton: Button? = null
    var noteMsg: String? = ""
    var noteDate: String? = ""
    private var mDatabaseReference: DatabaseReference? = null
    private var currUser: FirebaseUser? = null
    private var uId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editnote)
        val bundle = intent.extras
        val noteId = bundle!!.getString("id")
        noteMsg = bundle.getString("message")
        noteDate = bundle.getString("date")
        noteEditText = findViewById(R.id.editMsgNote)
        cancelButton = findViewById(R.id.cancelEditNoteButton)
        updateButton = findViewById(R.id.updateEditNoteButton)
        deleteButton = findViewById(R.id.deleteEditNoteButton)
        noteEditText!!.setText(noteMsg)
        mDatabaseReference = FirebaseDatabase.getInstance().getReference("notes")
        currUser = FirebaseAuth.getInstance().currentUser
        uId = currUser!!.uid
        //        String dbId = mDatabaseReference.getKey();
        updateButton!!.setOnClickListener(View.OnClickListener { updateNote(noteId) })
        deleteButton!!.setOnClickListener(View.OnClickListener { deleteNote(noteId) })
        cancelButton!!.setOnClickListener(View.OnClickListener { cancelEditing() })
    }

    private fun updateNote(id: String?) {
        noteMsg = noteEditText!!.text.toString()
        val editNoteList = Note(id, noteMsg, noteDate)
        mDatabaseReference!!.child(uId!!).child(id!!).setValue(editNoteList).addOnCompleteListener {
            Toast.makeText(this@EditNoteActivity, "Note updated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, NotesActivity::class.java))
        }
    }

    private fun deleteNote(id: String?) {
        mDatabaseReference!!.child(uId!!).child(id!!).removeValue().addOnSuccessListener {
            Toast.makeText(this@EditNoteActivity, "Note deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, NotesActivity::class.java))
        }
    }

    private fun cancelEditing() {
        Toast.makeText(this@EditNoteActivity, "No change made", Toast.LENGTH_SHORT).show()
        startActivity(Intent(applicationContext, NotesActivity::class.java))
    }
}