package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import android.widget.TextView
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
import com.example.keepsafe.PasswordActivity
import java.lang.StringBuilder
import java.util.*

class GeneratePasswordActivity : AppCompatActivity() {
    var accountText: EditText? = null
    var passwordLen: EditText? = null
    var generatePasswordButton: Button? = null
    var doneButton: Button? = null
    var cancelGenPassButton: Button? = null
    var genPassView: TextView? = null
    var accountTag: String? = null
    var genPassword: String? = null
    var passLen = 0
    private var databaseReference2: DatabaseReference? = null
    private var currUser: FirebaseUser? = null
    private var uId: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generatepassword)
        accountText = findViewById(R.id.passwordTagText)
        passwordLen = findViewById(R.id.passwordLength)
        genPassView = findViewById(R.id.genPasswordView)
        doneButton = findViewById(R.id.afterGenDoneButton)
        databaseReference2 = FirebaseDatabase.getInstance().getReference("passwords")
        currUser = FirebaseAuth.getInstance().currentUser
        uId = currUser!!.uid
        generatePasswordButton = findViewById(R.id.generatePasswordButton)
        generatePasswordButton!!.setOnClickListener(View.OnClickListener { v -> generatePassword(v) })
        cancelGenPassButton = findViewById(R.id.cancelGenPasswordButton)
        cancelGenPassButton!!.setOnClickListener(View.OnClickListener { cancelOperation() })
    }

    fun generatePassword(view: View?) {
        accountTag = accountText!!.text.toString()
        passLen = passwordLen!!.text.toString().toInt()
        if (TextUtils.isEmpty(accountTag)) return
        val chars = ("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghi"
                + "jklmnopqrstuvwxyz!@#$%&")
        val rnd = Random()
        val sb = StringBuilder(passLen)
        for (i in 0 until passLen) sb.append(chars[rnd.nextInt(chars.length)])
        genPassword = sb.toString()
        genPassView!!.text = genPassword
        generatePassword(accountTag!!, genPassword!!)
    }

    private fun generatePassword(account: String, password: String) {
        val id = databaseReference2!!.child(uId!!).push().key
        val passList = Password(id, account, password)
        databaseReference2!!.child(uId!!).child(id!!).setValue(passList).addOnCompleteListener {
            Toast.makeText(this@GeneratePasswordActivity, "Password generated", Toast.LENGTH_SHORT)
                .show()
            generatePasswordButton!!.text = "GENERATED"
            genPassView!!.visibility = View.VISIBLE
            doneButton!!.visibility = View.VISIBLE
            doneButton!!.setOnClickListener {
                startActivity(
                    Intent(
                        applicationContext,
                        PasswordActivity::class.java
                    )
                )
            }
        }
    }

    private fun cancelOperation() {
        startActivity(Intent(applicationContext, PasswordActivity::class.java))
    }
}