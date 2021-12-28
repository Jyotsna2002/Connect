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
import com.example.connect.Repository.LoginRepo
import com.example.connect.Repository.Response
import com.example.connect.databinding.LoginFragmentBinding

class Login_Fragment: Fragment() {
    private var _binding: LoginFragmentBinding?=null
    private val binding get() = _binding!!
    private lateinit var loginRepo: LoginRepo
    private fun isValidString(str: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(str).matches()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        val loginButton= binding.loginBtn

        loginButton.setOnClickListener {
            val loginEmail = binding.loginEmailEdit.text.toString().trim()
            val loginPassword = binding.loginPasswordEdit.text.toString().trim()
            if (isValid(loginEmail,loginPassword)) {
                loginRepo = LoginRepo()
                loginRepo.loginApi(loginEmail, loginPassword)
                loginRepo.loginResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "LogedIn", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_login_Fragment_to_dashboard)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                })
            }
        }
        binding.signup.setOnClickListener {  Navigation.findNavController(view).navigate(R.id.action_login_Fragment_to_signUp_Fragment) }
        return view
    }
    fun isValid(email:String,password:String):Boolean{
        return when{
            email.isBlank()->{
                binding.loginEmail.helperText="Enter Your Email Id"
                false
            }
            !EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.loginEmail.helperText = "Enter valid Email Id"
                false
            }
            password.isBlank()->{
                binding.loginPassword.helperText="Enter Your Password"
                false
            }
            else -> {
                true
            }
        }
    }
}