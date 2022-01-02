package com.example.connect.Views.Auth

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.connect.R
import com.example.connect.Repository.ForgetPasswordRepo
import com.example.connect.Repository.LoginRepo
import com.example.connect.Repository.Response
import com.example.connect.Views.Auth.Login_Fragment.Companion.forget
import com.example.connect.databinding.ForgetPasswordFragmentBinding
import com.example.connect.databinding.LoginFragmentBinding

class ForgetPassword_Fragment:Fragment() {
    private var _binding: ForgetPasswordFragmentBinding?=null
    private val binding get() = _binding!!
    private lateinit var forgetPasswordRepo: ForgetPasswordRepo
companion object{

    lateinit var email: String
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = ForgetPasswordFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        forget="false"

        val forgetButton= binding.ForgetBtn
        val progressBar=binding.ForgetProgressBar
        forgetButton.setOnClickListener {
            val forgetEmail = binding.ForgetEmailEdit.text.toString().trim()
            if (isValid(forgetEmail)) {
                progressBar.visibility = View.VISIBLE
                forgetButton.isClickable = false
                forgetPasswordRepo = ForgetPasswordRepo()
                forgetPasswordRepo.verifyApi(forgetEmail)
                forgetPasswordRepo.VerifyResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "Verify Otp", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                            forget = "true"
                            email = forgetEmail
                            Navigation.findNavController(view)
                                .navigate(R.id.action_forgetPassword_Fragment_to_otp_Fragment)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                            progressBar.visibility = View.GONE
                            forgetButton.isClickable = true
                        }

                        else -> {
                            forgetButton.isClickable = true
                        }
                    }
                })
            }
        }
        return view
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                view?.let {
                    Navigation.findNavController(it)
                        .navigate(R.id.action_forgetPassword_Fragment_to_login_Fragment)
                }
            }
        })
    }
    fun isValid(email:String):Boolean{
        return when{
            email.isBlank()->{
                binding.ForgetEmail.helperText="Enter Your Email Id"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                binding.ForgetEmail.helperText = "Enter valid Email Id"
                false
            }
            else -> {
                true
            }
        }
    }

}