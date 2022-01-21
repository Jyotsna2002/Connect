package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.AcceptRequestRepo
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.model.Profile
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class AcceptRequestViewModel(private val acceptRepo: AcceptRequestRepo) : ViewModel() {
    var Follow_id =  MutableLiveData<Int>()
    var Confirm =  MutableLiveData<String>()

    private var AcceptRequestResult: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val acceptRequestResult: LiveData<Response<ResponseBody>>
        get() = AcceptRequestResult
    fun submitotherprofile() = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${Follow_id.value?.toInt()}${Confirm.value}")
        AcceptRequestResult= acceptRepo.AcceptRequest(Follow_id.value,Confirm.value)
    }
}