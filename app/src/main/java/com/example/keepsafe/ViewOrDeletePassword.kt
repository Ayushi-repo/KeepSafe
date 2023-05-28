package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.TextView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.auth.FirebaseUser
import android.os.Bundle
import com.example.keepsafe.R
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.tasks.OnSuccessListener
import android.widget.Toast
import android.content.Intent
import android.view.View
import android.widget.Button
import com.example.keepsafe.PasswordActivity

class ViewOrDeletePassword : AppCompatActivity() {
    var accViewTextView: TextView? = null
    var passViewTextView: TextView? = null
    var cancelPButton: Button? = null
    var deletePButton: Button? = null
    var accTag: String? = ""
    var passwrd: String? = ""
    private var mDatabaseReference2: DatabaseReference? = null
    private var currUser: FirebaseUser? = null
    private var uId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewordelete_password)
        val bundle = intent.extras
        val passId = bundle!!.getString("pId")
        accTag = bundle.getString("account")
        passwrd = bundle.getString("password")
        accViewTextView = findViewById(R.id.accountView)
        passViewTextView = findViewById(R.id.passwordView)
        cancelPButton = findViewById(R.id.cancelPasswordButton)
        deletePButton = findViewById(R.id.deletePasswordButton)
        accViewTextView!!.setText(accTag)
        passViewTextView!!.setText(passwrd)
        mDatabaseReference2 = FirebaseDatabase.getInstance().getReference("passwords")
        currUser = FirebaseAuth.getInstance().currentUser
        uId = currUser!!.uid
        //        String dbId = mDatabaseReference.getKey();
        deletePButton!!.setOnClickListener(View.OnClickListener { deletePassword(passId) })
        cancelPButton!!.setOnClickListener(View.OnClickListener { cancelOperation() })
    }

    private fun deletePassword(id: String?) {
        mDatabaseReference2!!.child(uId!!).child(id!!).removeValue().addOnSuccessListener {
            Toast.makeText(this@ViewOrDeletePassword, "Password deleted", Toast.LENGTH_SHORT).show()
            startActivity(Intent(applicationContext, PasswordActivity::class.java))
        }
    }

    private fun cancelOperation() {
        startActivity(Intent(applicationContext, PasswordActivity::class.java))
    }
}