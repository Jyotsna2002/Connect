package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.OthersProfilePostRepo
import com.example.connect.Repository.OthersProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.model.OthersPost
import com.example.connect.model.Profile
import kotlinx.coroutines.launch

class OthersProfilePostViewModel(private val otherProfilepostRepo: OthersProfilePostRepo) : ViewModel() {
    var User_id =  MutableLiveData<Int>()

    private var ShowotherProfilePostResult: MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val showotherProfilPostResult: LiveData<Response<List<OthersPost>>>
        get() = ShowotherProfilePostResult
    fun submitotherprofilepost() = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${User_id.value?.toInt()}")
        ShowotherProfilePostResult= otherProfilepostRepo.OthersPostProfile(User_id.value)
    }
}