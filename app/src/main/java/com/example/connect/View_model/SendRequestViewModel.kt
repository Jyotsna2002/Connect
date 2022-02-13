package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.SendRequestRepo
import com.example.connect.model.EditProfileDataClass
import kotlinx.coroutines.launch

class SendRequestViewModel : ViewModel() {
    var User_id =  MutableLiveData<Int>()

    private var SendRequestResult: MutableLiveData<Response<EditProfileDataClass>> = MutableLiveData()
    val sendRequestResult: LiveData<Response<EditProfileDataClass>>
        get() = SendRequestResult
    fun sendRequest(context: Context) = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${User_id.value?.toInt()}")
        SendRequestResult= SendRequestRepo().SendRequest(User_id.value,context)
    }
}