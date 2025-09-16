package com.starmarine06.etherbase

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.starmarine06.etherbase.databinding.ActivityMainBinding
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
//import io.github.jan.supabase.plugins.
import kotlinx.coroutines.launch
import java.io.File
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var supabase: SupabaseClient
    private var imageUri: Uri = newImageUri()

    // Activity result launcher for taking a picture
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            Glide.with(this).load(imageUri).into(binding.capturedImageView)
            binding.uploadButton.isEnabled = true
        } else {
            Toast.makeText(this, "Picture not taken.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Supabase client
        supabase = SupabaseManager.supabaseClient


        binding.takePictureButton.setOnClickListener {
            imageUri = newImageUri()
            takePictureLauncher.launch(imageUri)
        }

        binding.uploadButton.setOnClickListener {
            uploadImage()
        }

        // Initially disable the upload button until a picture is taken
        binding.uploadButton.isEnabled = false
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in
        lifecycleScope.launch {
            val session = supabase.auth.currentSessionOrNull()
            if (session == null) {
                val intent = Intent(this@MainActivity, SignInActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                binding.welcomeTextView.text = "Logged in as: ${session.user?.email}"
            }
        }
    }

    private fun showLogoutDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to log out?")

        builder.setPositiveButton("Yes") { _, _ ->
            lifecycleScope.launch {
                try {
                    supabase.auth.signOut()
                    val intent = Intent(this@MainActivity, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this@MainActivity, "Logout failed: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val dialog = builder.create()
        dialog.show()
    }

    private fun newImageUri(): Uri {
        val imagesFolder = externalCacheDir
        val imageFile = File(imagesFolder, "${UUID.randomUUID()}.jpg")
        return Uri.fromFile(imageFile)
    }

    private fun uploadImage() {
        val imageName = binding.imageNameEditText.text.toString().trim()
        val fileUri = imageUri

        if (imageName.isEmpty() || fileUri == null) {
            Toast.makeText(this, "Please enter a name and take a picture.", Toast.LENGTH_SHORT).show()
            return
        }

        val user = supabase.auth.currentSessionOrNull()?.user ?: return

        lifecycleScope.launch {
            try {
                // The path includes the user's ID to organize uploads per user
                val path = "users/${user.id}/$imageName.jpg"

                val imageBytes = contentResolver.openInputStream(fileUri)?.readBytes()
                if (imageBytes != null) {
                    supabase.storage.from("images").upload(path, imageBytes)
                    Toast.makeText(this@MainActivity, "Upload successful!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Failed to read image data.", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(this@MainActivity, "Upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}