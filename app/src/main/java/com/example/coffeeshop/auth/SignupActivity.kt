package com.example.coffeeshop.auth

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.coffeeshop.MainActivity
import com.example.coffeeshop.R
import com.google.firebase.auth.FirebaseAuth

class SignupActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var firstName: EditText
    private lateinit var lastName: EditText
    private lateinit var email: EditText
    private lateinit var password: EditText
    private lateinit var day: EditText
    private lateinit var month: EditText
    private lateinit var year: EditText
    private lateinit var acceptCheckBox: CheckBox
    private lateinit var signupButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        auth = FirebaseAuth.getInstance()

        firstName = findViewById(R.id.firstNameEditText)
        lastName = findViewById(R.id.lastNameEditText)
        email = findViewById(R.id.emailEditText)
        password = findViewById(R.id.passwordEditText)
        day = findViewById(R.id.dayEditText)
        month = findViewById(R.id.monthEditText)
        year = findViewById(R.id.yearEditText)
        acceptCheckBox = findViewById(R.id.acceptCheckBox)
        signupButton = findViewById(R.id.signupButton)

        signupButton.setOnClickListener {
            val first = firstName.text.toString().trim()
            val last = lastName.text.toString().trim()
            val mail = email.text.toString().trim()
            val pass = password.text.toString()
            val bday = "${day.text}/${month.text}/${year.text}"

            if (first.isEmpty() || last.isEmpty() || mail.isEmpty() || pass.isEmpty()
                || day.text.isEmpty() || month.text.isEmpty() || year.text.isEmpty()
            ) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!acceptCheckBox.isChecked) {
                Toast.makeText(this, "You must accept the terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // TODO: Optionally save first, last, bday info to Firestore or Realtime Database
                        Toast.makeText(this, "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }
}
