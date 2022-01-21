package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.SendRequestRepo
import com.example.connect.model.Profile
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class SendRequestViewModel(private val sendRequestRepo: SendRequestRepo) : ViewModel() {
    var User_id =  MutableLiveData<Int>()

    private var SendRequestResult: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val sendRequestResult: LiveData<Response<ResponseBody>>
        get() = SendRequestResult
    fun sendRequest() = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${User_id.value?.toInt()}")
        SendRequestResult= sendRequestRepo.SendRequest(User_id.value)
    }
}