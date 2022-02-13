package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.OthersProfilePostRepo
import com.example.connect.Password_check.Response
import com.example.connect.model.OthersPost
import kotlinx.coroutines.launch

class OthersProfilePostViewModel : ViewModel() {
    var User_id =  MutableLiveData<Int>()

    private var ShowotherProfilePostResult: MutableLiveData<Response<List<OthersPost>>> = MutableLiveData()
    val showotherProfilPostResult: LiveData<Response<List<OthersPost>>>
        get() = ShowotherProfilePostResult
    fun submitotherprofilepost(context: Context) = viewModelScope.launch {
        Log.i("User", "onActivityResult: ${User_id.value?.toInt()}")
        ShowotherProfilePostResult= OthersProfilePostRepo().OthersPostProfile(User_id.value,context)
    }
}