package com.example.connect.Views.Dashboard

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.connect.R
import com.example.connect.Repository.*
import com.example.connect.Views.Auth.SignUp_Fragment
import com.example.connect.databinding.UsernameFragmentBinding
import com.example.connect.model.AuthDataClass
import kotlinx.coroutines.launch

class Username_Fragment :Fragment() {
    private var _binding: UsernameFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var usernameRepo: UsernameRepo
    lateinit var datastore: Datastore
companion object{
    lateinit var username:String
}
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = UsernameFragmentBinding.inflate(inflater, container, false)
        val view = binding.root

        val usernameButton= binding.UsernameBtn
        val progressBar=binding.usernameProgressBar
        usernameButton.setOnClickListener {
            val Username = binding.usernameEditText.text.toString().trim()
            // Log.i("email", "onActivityResult: $loginEmail")
            if (isValid(Username)) {
                progressBar.visibility = View.VISIBLE
                usernameButton.isClickable = false
                usernameRepo = UsernameRepo()
                usernameRepo.Createusername(Username)
                usernameRepo.UsernameResponse.observe(viewLifecycleOwner, {
                    when (it) {
                        is Response.Success -> {

                            Toast.makeText(context, "Username is set", Toast.LENGTH_SHORT).show()
                            progressBar.visibility = View.GONE
                            username=Username
//                            usernameRepo.userDetails.observe(viewLifecycleOwner, {
                            Log.i("username","response"+it)
//                                datastore = Datastore(requireContext())
//                                lifecycleScope.launch {
//                                    datastore.saveToDatastore( AuthDataClass(
//                                        username=it.username
//                                    ), requireContext())
//                                    activity?.finish()
//                                }
//                            })
                            Navigation.findNavController(view)
                                .navigate(R.id.action_username_Fragment_to_signUp_Fragment)
                        }

                        is Response.Error -> {
                            Toast.makeText(context, it.errorMessage.toString(), Toast.LENGTH_SHORT)
                                .show()
                            progressBar.visibility = View.GONE
                            usernameButton.isClickable = true
                        }

                        else -> {
                            usernameButton.isClickable = true
                        }
                    }
                })
            }
        }
    return view
}
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
//    val usernameRepo = UsernameRepo(ServiceBuilder1.buildService(Dashboard.token))
//    Log.i("token", "access:${Dashboard.token}")
//    val postViewModelFactory = PostViewModelFactory(usernameRepo)
//    postViewModel = ViewModelProvider(this, postViewModelFactory)[PostViewModel::class.java]
}
fun isValid(username:String):Boolean{
    return when{
        username.isBlank()->{
            binding.usernameLayout.helperText="Username can not be empty"
            false
        }
        else -> {
            true
        }
    }
}

}