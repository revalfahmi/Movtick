package com.example.movtick.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.movtick.R
import com.example.movtick.model.User
import com.google.firebase.database.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var etName: EditText
    private lateinit var etEmail: EditText
    private lateinit var btnNext: Button

    lateinit var sUsername: String
    lateinit var sPassword: String
    lateinit var sName: String
    lateinit var sEmail: String

    lateinit var mDatabaseReference: DatabaseReference
    lateinit var mFirebaseInstance: FirebaseDatabase
    lateinit var mDatabase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        etUsername = findViewById(R.id.et_username)
        etPassword = findViewById(R.id.et_password)
        etName = findViewById(R.id.et_name)
        etEmail = findViewById(R.id.et_email)
        btnNext = findViewById(R.id.btn_next_photo)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mDatabaseReference = mFirebaseInstance.getReference("User")

        btnNext.setOnClickListener {
            sUsername = etUsername.text.toString()
            sPassword = etPassword.text.toString()
            sName = etName.text.toString()
            sEmail = etEmail.text.toString()

            if (sUsername.equals("")){
                etUsername.error = "Username is required."
                etUsername.requestFocus()
            } else if (sPassword.equals("")){
                etPassword.error = "Password is required."
                etPassword.requestFocus()
            } else if (sName.equals("")){
                etName.error = "Name is requuired."
                etName.requestFocus()
            } else if (sEmail.equals("")){
                etEmail.error = "Email is required."
                etEmail.requestFocus()
            } else {
                saveUsername(sUsername, sPassword, sName, sEmail)
            }
        }
    }

    private fun saveUsername(sUsername: String, sPassword: String, sName: String, sEmail: String) {
        var user = User()
        user.username = sUsername
        user.password = sPassword
        user.nama = sName
        user.email = sEmail

        if (sUsername != null){
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(sUsername: String, data: User) {
        mDatabaseReference.child(sUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.getValue(User::class.java)
                if (user != null){
                    Toast.makeText(this@SignUpActivity, "Username has been registered", Toast.LENGTH_LONG).show()
                }else{
                    mDatabaseReference.child(sUsername).setValue(data)

                    val intent = Intent(this@SignUpActivity,
                        SignUpPhotoScreenActivity::class.java).putExtra("nama", data.nama)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+error.message, Toast.LENGTH_LONG).show()
            }

        })
    }
}