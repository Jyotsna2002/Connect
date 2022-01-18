package com.example.connect.Views.Auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.connect.Password_check.validPass
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Email
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Name
import com.example.connect.R
import com.example.connect.Repository.CreatePasswordRepo
import com.example.connect.Repository.Datastore
import com.example.connect.Repository.Response
import com.example.connect.model.AuthDataClass
import com.example.connect.Views.Auth.ForgetPassword_Fragment.Companion.email
import com.example.connect.Views.Auth.LandingPage_Fragment.Companion.forget
import com.example.connect.Views.Dashboard.Username_Fragment.Companion.username
import com.example.connect.databinding.CreatePasswordFragmentBinding
import kotlinx.coroutines.launch

class CreatePassword_Fragment:Fragment() {
    private var _binding: CreatePasswordFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var createPasswordRepo: CreatePasswordRepo
    lateinit var datastore: Datastore
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = CreatePasswordFragmentBinding.inflate(inflater, container, false)
        val view = binding.root
        val passwordButton = binding.passwordBtn
        val progressBar= binding.createPasswordProgressBar
        if(forget=="true") {
            binding.psswrd.setText("Reset Password")
            binding.changePassword.setText("Your new password must be different from previous used password")
            binding.createPasswordEdit.hint="New Password"
            binding.confirmPasswordEdit.hint="Old Password"
        }
        else{
            binding.createPasswordEdit.hint="Password"
            binding.confirmPasswordEdit.hint="Confirm Password"
        }
            passwordButton.setOnClickListener {
            val password = binding.createPasswordEdit.text.toString().trim()
            val confirm= binding.confirmPasswordEdit.text.toString().trim()
            if(forget=="true")
            {
                if(isValidated(password,confirm)) {
                    passwordButton.isClickable = false
                    progressBar.visibility = View.VISIBLE
                    createPasswordRepo = CreatePasswordRepo()
                    createPasswordRepo.ForgetPassword(email, password)
                    createPasswordRepo.passwordResponse.observe(viewLifecycleOwner, {
                        when (it) {
                            is Response.Success -> {

                                Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                                createPasswordRepo.userData.observe(viewLifecycleOwner, {
                                    datastore = Datastore(requireContext())
                                    lifecycleScope.launch {
                                        datastore.saveToDatastore(
                                            AuthDataClass(
                                                email = email,
                                                name = it.name,
                                                access = it.access,
                                                refresh = it.refresh
                                            ),
                                            requireContext()
                                        )
                                        activity?.finish()
                                    }
                                })
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_createPassword_Fragment_to_dashboard)
                            }

                            is Response.Error -> {
                                Toast.makeText(
                                    context,
                                    it.errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                passwordButton.isClickable = true
                                progressBar.visibility = View.GONE
                            }

                            else -> {
                                passwordButton.isClickable = true
                            }
                        }
                    })
                }
            }
            if(forget=="false")
            if(isValid(password,confirm)) {
                passwordButton.isClickable=false
                progressBar.visibility=View.VISIBLE
                createPasswordRepo = CreatePasswordRepo()
                createPasswordRepo.passwordApi(Email, Name, password,username)
                createPasswordRepo.passwordResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "Welcome", Toast.LENGTH_SHORT).show()
                            progressBar.visibility=View.GONE
                            createPasswordRepo.userData.observe(viewLifecycleOwner, {
                                datastore = Datastore(requireContext())
                                lifecycleScope.launch {
                                    datastore.saveToDatastore(
                                        AuthDataClass(
                                            email = Email,
                                            name = Name,
                                            access = it.access,
                                            refresh = it.refresh,
                                            username=it.username,
                                            user=it.user
                                        ),
                                        requireContext()
                                    )
                                    activity?.finish()
                                }
                            })
                            Navigation.findNavController(view)
                                .navigate(R.id.action_createPassword_Fragment_to_dashboard)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                            passwordButton.isClickable=true
                            progressBar.visibility=View.GONE
                        }

                        else -> {
                            passwordButton.isClickable=true
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
                        .navigate(R.id.action_createPassword_Fragment_to_login_Fragment)
                }
            }
        })
    }
    fun isValid(pass: String, confirmPass: String): Boolean
    {
       return when{
            pass.isBlank()->{
                binding.createPassword.helperText="Enter Your Password"
                false
            }
           pass.isBlank()->{
               binding.confirmPassword.helperText="Confirm Your Password"
               false
           }
           pass!=confirmPass->{
               binding.confirmPassword.helperText="Confirm Password does not match"
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
    fun isValidated(pass: String, confirmPass: String): Boolean
    {
        return when{
            pass.isBlank()->{
                binding.createPassword.helperText="Enter New Password"
                false
            }
            pass.isBlank()->{
                binding.confirmPassword.helperText="Enter Old Password"
                false
            }
            pass==confirmPass->{
                binding.confirmPassword.helperText="New Password and old password must be different"
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