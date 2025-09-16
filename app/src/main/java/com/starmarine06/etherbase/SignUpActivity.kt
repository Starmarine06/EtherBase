package com.starmarine06.etherbase

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.starmarine06.etherbase.databinding.ActivitySignUpBinding
import io.supabase.supabase.SupabaseClient
import kotlinx.coroutines.launch

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var supabase: SupabaseClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supabase = SupabaseManager.supabaseClient

        binding.createAccountButton.setOnClickListener {
            val email = binding.signUpEmailEditText.text.toString().trim()
            val password = binding.signUpPasswordEditText.text.toString().trim()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                // Use a coroutine to make the network request
                lifecycleScope.launch {
                    try {
                        supabase.auth.signUpWith(email, password)
                        Toast.makeText(this@SignUpActivity, "Account created! Check your email to confirm.", Toast.LENGTH_LONG).show()
                        finish() // Go back to sign-in page
                    } catch (e: Exception) {
                        Toast.makeText(this@SignUpActivity, "Sign-up failed: ${e.message}", Toast.LENGTH_LONG).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please enter email and password.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}