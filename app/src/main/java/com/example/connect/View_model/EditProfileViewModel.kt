package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.EditProfileRepo
import com.example.connect.Password_check.Response
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class EditProfileViewModel : ViewModel() {

    var Username = MutableLiveData<String>()
    var Bio = MutableLiveData<String>()
    var ProfilePhoto =  MutableLiveData<String>()
    var private= MutableLiveData<Boolean>()

    private var EditResult:MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val editResult:LiveData<Response<ResponseBody>>
        get() = EditResult

    fun EditProfileSubmitData(context: Context) = viewModelScope.launch {
        EditResult =EditProfileRepo().EditProfile(Username.value, Bio.value, ProfilePhoto.value,private.value,context)

    }
}
