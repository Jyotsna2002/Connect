package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.OthersProfilePostRepo
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.SeeTagPostRepo
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import kotlinx.coroutines.launch

class SeeTagViewModel(private val seeTagRepo: SeeTagPostRepo) : ViewModel() {
    var Tag =  MutableLiveData<String>()

    private var SeePostResult: MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val seePostResult: LiveData<Response<List<OthersPost>>>
        get() = SeePostResult
    fun submitTagPost() = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${Tag.value}")
        SeePostResult= seeTagRepo.SeeTag(Tag.value)
    }
}