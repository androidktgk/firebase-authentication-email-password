package com.example.explorefirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnSignIn: Button
    private lateinit var edtEmail: EditText
    private lateinit var edtPass: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        firebaseAuth=Firebase.auth
        btnSignIn=findViewById(R.id.btnSignIn)
        edtEmail=findViewById(R.id.edtEmailIn)
        edtPass=findViewById(R.id.edtPassIn)
        firebaseAuth.addAuthStateListener {
            if (it.currentUser!=null){
                startActivity(Intent(this,MainActivity::class.java))
            }
        }

        btnSignIn.setOnClickListener{
            val email=edtEmail.text.toString()
            val password=edtPass.text.toString()

            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener {
                if (it.isSuccessful){
                    startActivity(Intent(this,MainActivity::class.java))
                }else{
                    Toast.makeText(this,"Error Ocurred while signing!", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}