package com.example.connect.Views.Auth

import android.os.Bundle
import android.util.Patterns.EMAIL_ADDRESS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.connect.R
import com.example.connect.Repository.Datastore
import com.example.connect.Repository.Response
import com.example.connect.Repository.SignUpRepo
import com.example.connect.databinding.SignupFragmentBinding

class SignUp_Fragment:Fragment() {
    companion object {
        lateinit var Email: String
        lateinit var Name: String
    }
        private var _binding: SignupFragmentBinding?=null
        private val binding get() = _binding!!
        private lateinit var signUpRepo: SignUpRepo
        lateinit var datastore: Datastore

    private fun isValidString(str: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
        }
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {
            _binding = SignupFragmentBinding.inflate(inflater, container, false)
            val view = binding.root
            datastore = Datastore(requireContext())
            val signUpButton= binding.signUpBtn
            signUpButton.setOnClickListener {
                val signUpEmail = binding.signUpEmailEdit.text.toString().trim()
                val signUpName = binding.signUpNameEdit.text.toString().trim()
                if (isValid(signUpName, signUpEmail)) {
                    signUpRepo = SignUpRepo()
                    signUpRepo.signUpApi(signUpEmail, signUpName)
                    signUpRepo.signUPResponse.observe(viewLifecycleOwner, {
                        when (it) {
                            is Response.Success -> {

                                Toast.makeText(context, "verify your otp", Toast.LENGTH_SHORT)
                                    .show()
                                Email = signUpEmail
                                Name = signUpName
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_signUp_Fragment_to_otp_Fragment)
                            }

                            is Response.Error -> {
                                Toast.makeText(
                                    context,
                                    it.errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }

                        }
                    })
                }
            }
            binding.login.setOnClickListener { Navigation.findNavController(view).navigate(R.id.action_signUp_Fragment_to_login_Fragment) }
        return view
    }
    fun isValid(name:String,email:String):Boolean{
        return when{
            name.isBlank()->{
                binding.signUpName.helperText="Enter Your Name"
                false
            }
            email.isBlank()->{
                binding.signUpEmail.helperText="Enter Your Email Id"
                false
            }
            !EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.signUpEmail.helperText = "Enter valid Email Id"
                false
            }
            else -> {
                true
            }
        }
    }
}