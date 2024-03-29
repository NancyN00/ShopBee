package com.example.shopbee.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.shopbee.R
import com.example.shopbee.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {

    private lateinit var binding : ActivityStartBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            startActivity(intent)
         //   Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

        binding.regBtn.setOnClickListener {
            val intent = Intent(this@StartActivity, RegistrationActivity::class.java)
            startActivity(intent)
           // Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
        }

    }
}