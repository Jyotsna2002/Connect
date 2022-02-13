package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.AcceptRequestRepo
import com.example.connect.Password_check.Response
import kotlinx.coroutines.launch
import okhttp3.ResponseBody

class AcceptRequestViewModel : ViewModel() {
    var Follow_id =  MutableLiveData<Int>()
    var Confirm =  MutableLiveData<String>()

    private var AcceptRequestResult: MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val acceptRequestResult: LiveData<Response<ResponseBody>>
        get() = AcceptRequestResult
    fun submitotherprofile(context: Context) = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${Follow_id.value?.toInt()}${Confirm.value}")
        AcceptRequestResult= AcceptRequestRepo().AcceptRequest(Follow_id.value,Confirm.value,context)
    }
}