package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import android.os.Bundle
import com.example.keepsafe.R
import android.text.TextUtils
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.keepsafe.SignupActivity
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.keepsafe.UserActivity

class SignupActivity : AppCompatActivity() {
    private var nameTextView: EditText? = null
    private var emailTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var signUpButton: Button? = null

    //    private ProgressBar progressbar;
    private var mAuth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)
        mAuth = FirebaseAuth.getInstance()
        nameTextView = findViewById(R.id.nameText)
        emailTextView = findViewById(R.id.emailText)
        passwordTextView = findViewById(R.id.passwordText)
        signUpButton = findViewById(R.id.signUpButton)
        //        progressbar = findViewById(R.id.progressbar);
        signUpButton!!.setOnClickListener(View.OnClickListener { createAccount() })
    }

    private fun createAccount() {
//        progressbar.setVisibility(View.VISIBLE);
        val name: String
        val email: String
        val password: String
        name = nameTextView!!.text.toString()
        email = emailTextView!!.text.toString()
        password = passwordTextView!!.text.toString()
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(applicationContext, "Enter name", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth!!.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "createUserWithEmail:success")
                val user = mAuth!!.currentUser
                Toast.makeText(applicationContext, "Sign up successful!", Toast.LENGTH_SHORT).show()
                //                  progressBar.setVisibility(View.GONE);
                val intent = Intent(this@SignupActivity, UserActivity::class.java)
                startActivity(intent)
                updateUI(user)
            } else {
                Log.w(TAG, "createUserWithEmail:failure", task.exception)
                Toast.makeText(this@SignupActivity, "Sign up failed.", Toast.LENGTH_SHORT).show()
                //                  progressBar.setVisibility(View.GONE);
                updateUI(null)
            }
        }
    }

    private fun reload() {}
    private fun updateUI(user: FirebaseUser?) {}

    companion object {
        private const val TAG = "SignUp"
    }
}