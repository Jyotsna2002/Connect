package com.example.connect.Views.Auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Email
import com.example.connect.R
import com.example.connect.Repository.OtpRepo
import com.example.connect.Repository.Response
import com.example.connect.databinding.OtpFragmentBinding

class Otp_Fragment: Fragment() {
    private var _binding: OtpFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var otpRepo: OtpRepo

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OtpFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        val otpButton = binding.OtpBtn

        otpButton.setOnClickListener {
            val otp = binding.otpEmailEdit.text.toString().trim()
            if (isValid(otp)) {
                otpRepo = OtpRepo()
                otpRepo.otpApi(Email, otp)
                otpRepo.otpResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "correct otp", Toast.LENGTH_SHORT).show()
                            Navigation.findNavController(view)
                                .navigate(R.id.action_otp_Fragment_to_createPassword_Fragment)
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
    fun isValid(otp:String):Boolean{
        return when{
            otp.isBlank()->{
                binding.otpEmail.helperText="Enter otp"
                false
            }
            else -> {
                true
            }
        }
    }
}