package com.example.coffeeshop.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.coffeeshop.MainActivity
import com.example.coffeeshop.databinding.FragmentSignupBinding
import com.google.firebase.auth.FirebaseAuth

class SignupFragment : Fragment() {

    private var _binding: FragmentSignupBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.signupButton.setOnClickListener {
            val first = binding.firstNameEditText.text.toString().trim()
            val last = binding.lastNameEditText.text.toString().trim()
            val mail = binding.emailEditText.text.toString().trim()
            val pass = binding.passwordEditText.text.toString()
            val bday = "${binding.dayEditText.text}/${binding.monthEditText.text}/${binding.yearEditText.text}"

            if (first.isEmpty() || last.isEmpty() || mail.isEmpty() || pass.isEmpty()
                || binding.dayEditText.text.isEmpty() || binding.monthEditText.text.isEmpty() || binding.yearEditText.text.isEmpty()
            ) {
                Toast.makeText(requireContext(), "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (!binding.acceptCheckBox.isChecked) {
                Toast.makeText(requireContext(), "You must accept the terms", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(mail, pass)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Registration successful", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(requireContext(), MainActivity::class.java))
                        requireActivity().finish()
                    } else {
                        Toast.makeText(requireContext(), "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
