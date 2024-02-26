package com.example.shopbee.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.widget.Toast
import com.example.shopbee.HomeActivity
import com.example.shopbee.R
import com.example.shopbee.databinding.ActivityLoginBinding
import com.example.shopbee.fragments.HomeFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth

        binding.donthaveAcc.setOnClickListener {
            val intent = Intent(this@LoginActivity, RegistrationActivity::class.java)
            startActivity(intent)
        }

        fun String.toEditable(): Editable = Editable.Factory.getInstance().newEditable(this)

         binding.loginmailTxt.text = "ancyy@gmail.com".toEditable()
         binding.loginpassTxt.text = "12345".toEditable()

        binding.loginBtn.setOnClickListener {

            val logmail = binding.loginmailTxt.text.toString().trim()
            val logpass = binding.loginpassTxt.text.toString().trim()


            when {

                TextUtils.isEmpty(logmail) -> binding.loginmailTxt.error = "Email Required"
                TextUtils.isEmpty(logpass) -> binding.loginpassTxt.error = "Password Required"

                else -> {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                   Toast.makeText(this, "success", Toast.LENGTH_SHORT).show()
                }
            }

        }

    }
}