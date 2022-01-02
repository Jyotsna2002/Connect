package com.example.connect.Views.Auth

import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Email
import com.example.connect.R
import com.example.connect.Repository.ForgetPasswordRepo
import com.example.connect.Repository.OtpRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.SignUpRepo
import com.example.connect.Views.Auth.ForgetPassword_Fragment.Companion.email
import com.example.connect.Views.Auth.Login_Fragment.Companion.forget
import com.example.connect.Views.Auth.SignUp_Fragment.Companion.Name
import com.example.connect.databinding.OtpFragmentBinding
import android.text.Html




class Otp_Fragment: Fragment() {
    private var _binding: OtpFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var otpRepo: OtpRepo
    private lateinit var signUpRepo: SignUpRepo
    private lateinit var forgetPasswordRepo: ForgetPasswordRepo
    private lateinit var timerCountDown: CountDownTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = OtpFragmentBinding.inflate(inflater, container, false)
        val view = binding.root


        val otpButton = binding.OtpBtn
        val progressBar=binding.otpProgressBar
        val timer=binding.timer
        timerCountDown = object : CountDownTimer(31000, 1000) {

            override fun onTick(millisUntilFinished: Long) {
                timer.isEnabled = false
                timer.text = "Resend OTP in " + millisUntilFinished / 1000 + " sec"
            }

            override fun onFinish() {
                timer.isEnabled = true
                val text =
                    " Didn't receive otp?<font color=#EE336F> Resend Now</font>"
                timer.setText(Html.fromHtml(text))
            }
        }.start()
        timer.setOnClickListener {
            progressBar.visibility=View.VISIBLE
            timer.isClickable=false
            if(forget=="false") {
                signUpRepo = SignUpRepo()
                signUpRepo.signUpApi(Email, Name)
                signUpRepo.signUPResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            timer.isClickable = true
                            progressBar.visibility = View.GONE
                        }

                        is Response.Error -> {
                            Toast.makeText(
                                context,
                                it.errorMessage.toString(),
                                Toast.LENGTH_SHORT
                            ).show()
                            progressBar.visibility = View.GONE

                        }

                        else -> {
                            timer.isClickable = true
                        }
                    }
                })
            }
            else{
                forgetPasswordRepo = ForgetPasswordRepo()
                forgetPasswordRepo.verifyApi(email)
                forgetPasswordRepo.VerifyResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            progressBar.visibility=View.GONE
                            forget="true"
                            timer.isClickable = true
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                            progressBar.visibility=View.GONE

                        }

                        else -> {
                            timer.isClickable = true
                        }
                    }
                })
            }
        }
        otpButton.setOnClickListener {
            val otp = binding.otpEmailEdit.text.toString().trim()
            if(forget=="true")
            {
                if (isValid(otp)) {
                    otpButton.isClickable = false
                    progressBar.visibility = View.VISIBLE
                    otpRepo = OtpRepo()
                    otpRepo.EnterOtp(email, otp)
                    otpRepo.otpResponse.observe(viewLifecycleOwner, {
                        when (it) {
                            is Response.Success -> {

                                Toast.makeText(context, "correct otp", Toast.LENGTH_SHORT).show()
                                progressBar.visibility = View.GONE
                                timerCountDown.cancel()
                                Navigation.findNavController(view)
                                    .navigate(R.id.action_otp_Fragment_to_createPassword_Fragment)
                            }

                            is Response.Error -> {
                                Toast.makeText(
                                    context,
                                    it.errorMessage.toString(),
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                                otpButton.isClickable = true
                                progressBar.visibility = View.GONE
                            }

                            else -> {
                                otpButton.isClickable = true
                            }
                        }
                    })
                }

            }
            if(forget=="false")
            if (isValid(otp)) {
                otpButton.isClickable=false
                progressBar.visibility=View.VISIBLE
                otpRepo = OtpRepo()
                otpRepo.otpApi(Email, otp)
                otpRepo.otpResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "correct otp", Toast.LENGTH_SHORT).show()
                            progressBar.visibility=View.GONE
                            Navigation.findNavController(view)
                                .navigate(R.id.action_otp_Fragment_to_createPassword_Fragment)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                            otpButton.isClickable=true
                            progressBar.visibility=View.GONE
                        }

                        else -> {
                            otpButton.isClickable=true
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
                        .navigate(R.id.action_otp_Fragment_to_login_Fragment)
                }
            }
        })
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

    override fun onDestroy() {
        super.onDestroy()
        timerCountDown.cancel()
    }
}