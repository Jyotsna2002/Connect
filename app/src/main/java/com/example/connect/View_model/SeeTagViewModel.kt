package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.SeeTagPostRepo
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch

class SeeTagViewModel : ViewModel() {
    var Tag =  MutableLiveData<String>()

    private var SeePostResult: MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val seePostResult: LiveData<Response<List<OthersPost>>>
        get() = SeePostResult
    fun submitTagPost(context: Context) = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${Tag.value}")
        SeePostResult= SeeTagPostRepo().SeeTag(Tag.value,context)
    }
}