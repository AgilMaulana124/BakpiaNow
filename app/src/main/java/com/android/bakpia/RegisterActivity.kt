package com.android.bakpia

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.bakpia.model.User
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnRegister: Button

    val database = FirebaseDatabase.getInstance(
        "https://final-e3f9e-default-rtdb.asia-southeast1.firebasedatabase.app/"
    )
    private val usersRef = database.getReference("users")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        btnRegister = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener { register() }
    }

    private fun register() {
        val id = usersRef.push().key ?: return
        usersRef.child(id).setValue(
            User(
                edtUsername.text.toString(),
                edtPassword.text.toString()
            )
        )

        Toast.makeText(this, "Registered!", Toast.LENGTH_SHORT).show()
        finish()
    }
}
