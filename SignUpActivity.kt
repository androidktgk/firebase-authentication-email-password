package com.example.explorefirebase

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SignUpActivity : AppCompatActivity() {
    lateinit var firebaseAuth: FirebaseAuth
    private lateinit var btnSignUp: MaterialButton
    private lateinit var edtEmail: TextInputEditText
    private lateinit var edtPass: TextInputEditText
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth=Firebase.auth
        btnSignUp=findViewById(R.id.btnSignUp)
        edtEmail=findViewById(R.id.edtEmailUp)
        edtPass=findViewById(R.id.edtPassUp)


        btnSignUp.setOnClickListener{
            (getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(btnSignUp.windowToken,0)
            val email=edtEmail.text.toString()
            val password=edtPass.text.toString()
            if (fieldValidate(email,password)){
                firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                    if(it.isSuccessful){
                        firebaseAuth.signOut()
                        Toast.makeText(this,"SignedUp Successfully",Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this,"Error Ocurred while signing!",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        findViewById<TextView>(R.id.txtOpenSignIn).setOnClickListener{
            startActivity(Intent(this,SignInActivity::class.java))
        }


    }
    private fun fieldValidate(email:String,pass:String): Boolean{
        if (email.equals("")){
            edtEmail.error = "Email is required!"
            return false
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            edtEmail.error = "Email is inavalid!"
            return false
        }
        if (pass.length<8 || pass.length>12 || pass.equals("")){
            edtPass.error = "Password must be 8 to 12 characters!"
            return false
        }
        return true
    }
}