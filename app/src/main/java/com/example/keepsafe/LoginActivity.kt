package com.example.keepsafe

import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import android.widget.EditText
import android.widget.TextView
import android.os.Bundle
import com.example.keepsafe.R
import android.content.Intent
import com.example.keepsafe.SignupActivity
import com.google.firebase.auth.FirebaseUser
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.example.keepsafe.LoginActivity
import com.example.keepsafe.UserActivity

class LoginActivity : AppCompatActivity() {
    private var mAuth: FirebaseAuth? = null
    private var emailTextView: EditText? = null
    private var passwordTextView: EditText? = null
    private var loginButton: Button? = null
    private var signUpTextView: TextView? = null
    private var fgtPwdBtnView: TextView? = null
    private var regEmail: String? = null

    //    private ProgressBar progressbar;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = FirebaseAuth.getInstance()
        emailTextView = findViewById(R.id.editTextTextEmailAddress)
        passwordTextView = findViewById(R.id.editTextTextPassword)
        loginButton = findViewById(R.id.loginButton)
        signUpTextView = findViewById(R.id.signUpClickableText)
        fgtPwdBtnView = findViewById(R.id.forgotPasswordBtn)
        //        progressbar = findViewById(R.id.progressBar);
        loginButton!!.setOnClickListener(View.OnClickListener { signIn() })
        signUpTextView!!.setOnClickListener(View.OnClickListener {
            val signupIntent = Intent(this@LoginActivity, SignupActivity::class.java)
            startActivity(signupIntent)
        })
        fgtPwdBtnView!!.setOnClickListener(View.OnClickListener { showResetPasswordDialogBox() })
    }

    public override fun onStart() {
        super.onStart()
        val currentUser = mAuth!!.currentUser
        if (currentUser != null) {
            reload()
        }
        updateUI(currentUser)
    }

    private fun signIn() {
//        progressbar.setVisibility(View.VISIBLE);
        val email: String
        val password: String
        email = emailTextView!!.text.toString()
        password = passwordTextView!!.text.toString()
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email ID", Toast.LENGTH_SHORT).show()
            return
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password", Toast.LENGTH_SHORT).show()
            return
        }
        mAuth!!.signInWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                Log.d(TAG, "signInWithEmail:success")
                if (task.isSuccessful) {
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT)
                        .show()
                    val user = mAuth!!.currentUser
                    //                        progressBar.setVisibility(View.GONE);
                    val intent = Intent(this@LoginActivity, UserActivity::class.java)
                    startActivity(intent)
                    updateUI(user)
                } else {
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    //                        progressbar.setVisibility(View.GONE);
                    Toast.makeText(this@LoginActivity, "Authentication failed.", Toast.LENGTH_SHORT)
                        .show()
                    updateUI(null)
                }
            }
        }
    }

    private fun reload() {}
    private fun updateUI(user: FirebaseUser?) {}
    fun showResetPasswordDialogBox() {
        val builder = AlertDialog.Builder(this)
        val boxView = layoutInflater.inflate(R.layout.fgtpwd_dialogbox_layout, null)
        val emailID = boxView.findViewById<View>(R.id.editTextRegEmail) as EditText
        val okButton = boxView.findViewById<View>(R.id.confirmPasswordReset) as Button
        val cancelButton = boxView.findViewById<View>(R.id.cancelPasswordReset) as Button
        builder.setView(boxView)
        val alertDialog = builder.create()
        alertDialog.setCanceledOnTouchOutside(false)
        okButton.setOnClickListener {
            regEmail = emailID.text.toString()
            resetPassword()
            alertDialog.dismiss()
        }
        cancelButton.setOnClickListener { alertDialog.dismiss() }
        alertDialog.show()
    }

    private fun resetPassword() {
        mAuth!!.sendPasswordResetEmail(regEmail!!).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                Log.i(TAG, "Password Reset Email sent.")
                Toast.makeText(this@LoginActivity, "Password Reset Email sent.", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    companion object {
        private const val TAG = "LogIn"
    }
}