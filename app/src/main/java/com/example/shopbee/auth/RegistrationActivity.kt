package com.example.shopbee.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.shopbee.R
import com.example.shopbee.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.haveAcc.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
            startActivity(intent)

        }

        binding.regBtn.setOnClickListener {
            val usermail = binding.regmailTxt.text.toString().trim()
            val userpass = binding.regpassTxt.text.toString().trim()

            if (usermail.isEmpty()) {
                binding.regmailTxt.error = "Email Required"
                return@setOnClickListener
            }

            if (userpass.isEmpty()) {
                binding.regpassTxt.error = "Password Required"
                return@setOnClickListener
            }
            auth.createUserWithEmailAndPassword(usermail, userpass).addOnCompleteListener {
                if (it.isSuccessful) {
                    val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this, "Registered Successfully", Toast.LENGTH_SHORT).show()

                } else {

                    Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show()
                }

            }
        }

    }
}