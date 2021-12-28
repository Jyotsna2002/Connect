package com.example.connect.Views.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.connect.Password_check.validPass
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Email
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Name
import com.example.connect.R
import com.example.connect.Repository.CreatePasswordRepo
import com.example.connect.Repository.Response
import com.example.connect.databinding.CreatePasswordFragmentBinding

class CreatePassword_Fragment:Fragment() {
    private var _binding: CreatePasswordFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var createPasswordRepo: CreatePasswordRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreatePasswordFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        val passwordButton = binding.passwordBtn

        passwordButton.setOnClickListener {
            val password = binding.createPasswordEdit.text.toString().trim()
            val confirm= binding.confirmPasswordEdit.text.toString().trim()
            if(isValid(password,confirm)) {
                createPasswordRepo = CreatePasswordRepo()
                createPasswordRepo.passwordApi(Email, Name, password)
                createPasswordRepo.passwordResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_createPassword_Fragment_to_dashboard)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                })
            }
        }
        return view
    }
    fun isValid(pass: String, confirmPass: String): Boolean
    {
       return when{
            pass.isBlank()->{
                binding.createPassword.helperText="Enter Your Password"
                false
            }
           pass.isBlank()->{
               binding.createPassword.helperText="Confirm Your Password"
               false
           }
           pass!=confirmPass->{
               binding.createPassword.helperText="Confirm Password does not match"
               false
           }
           validPass(pass)!=null->{
               binding.createPassword.helperText= validPass(pass)
               false
           }
           else -> {
               true
           }
       }
    }
}