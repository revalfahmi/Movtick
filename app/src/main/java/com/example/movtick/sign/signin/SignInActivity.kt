package com.example.movtick.sign.signin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.movtick.HomeActivity
import com.example.movtick.R
import com.example.movtick.model.User
import com.example.movtick.sign.signup.SignUpActivity
import com.example.movtick.utils.Preferences
import com.google.firebase.database.*

class SignInActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button

    private lateinit var iUsername: String
    private lateinit var iPassword: String

    lateinit var mDatabase: DatabaseReference
    lateinit var preferences: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        btnSignIn = findViewById(R.id.btn_sign_in)
        btnSignUp = findViewById(R.id.btn_sign_up)

        preferences = Preferences(this)

        mDatabase = FirebaseDatabase.getInstance().getReference("User")

        preferences.setValue("onboarding", "1")
        if (preferences.getValue("status").equals("1")){
            finishAffinity()

            val intent = Intent(this@SignInActivity, HomeActivity::class.java)
            startActivity(intent)
        }
        btnSignIn.setOnClickListener {
            iUsername = etUsername.text.toString()
            iPassword = etPassword.text.toString()

            if (iUsername.equals("")){
                etUsername.error = "Username is required."
                etUsername.requestFocus()
            } else if (iPassword.equals("")){
                etPassword.error = "Password is required."
                etPassword.requestFocus()
            } else {
                pushLogin(iUsername, iPassword)
            }
        }

        btnSignUp.setOnClickListener {
            val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun pushLogin(iUsername: String, iPassword: String) {
        mDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null){
                    Toast.makeText(this@SignInActivity, "Username not found in database.", Toast.LENGTH_LONG).show()
                }else{
                    if (user.password.equals(iPassword)){
                        preferences.setValue("nama", user.nama.toString())
                        preferences.setValue("username", user.username.toString())
                        preferences.setValue("password", user.password.toString())
                        preferences.setValue("saldo", user.saldo.toString())
                        preferences.setValue("url", user.url.toString())
                        preferences.setValue("email", user.email.toString())
                        preferences.setValue("status", "1")

                        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@SignInActivity, "Password is incorrect.", Toast.LENGTH_LONG).show()
                    }

                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@SignInActivity, ""+databaseError.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}