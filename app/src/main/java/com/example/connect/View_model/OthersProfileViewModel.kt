package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.model.Profile
import kotlinx.coroutines.launch

class OthersProfileViewModel(private val otherProfileRepo: OthersProfileRepo) : ViewModel() {
    var User_id =  MutableLiveData<Int>()

    private var ShowotherProfileResult: MutableLiveData<Response<Profile>> = MutableLiveData()
    val showotherProfilResult: LiveData<Response<Profile>>
        get() = ShowotherProfileResult
    fun submitotherprofile() = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${User_id.value?.toInt()}")
        ShowotherProfileResult= otherProfileRepo.OthersProfile(User_id.value)
    }
}