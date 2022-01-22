package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.EditProfileRepo
import com.example.connect.Repository.Response
import com.example.connect.Repository.UploadPostRepo
import com.example.connect.Views.Dashboard.Post_Fragment.Companion.choose
import com.example.connect.Views.Dashboard.Post_Fragment.Companion.ok
import com.example.connect.model.PostDataClass
import kotlinx.coroutines.launch
import okhttp3.ResponseBody


class EditProfileViewModel(private val editRepo: EditProfileRepo) : ViewModel() {

    var Username = MutableLiveData<String>()
    var Bio = MutableLiveData<String>()
    var ProfilePhoto =  MutableLiveData<String>()

    private var EditResult:MutableLiveData<Response<ResponseBody>> = MutableLiveData()
    val editResult:LiveData<Response<ResponseBody>>
        get() = EditResult

    fun EditProfileSubmitData() = viewModelScope.launch {
//        if(ok=="0")
//        {
//            imageUrl.value= emptyList()
//        }
//        else if(ok=="1"){
//            videoUrl.value= emptyList()
//        }
//        else
//        {
//            caption.value=""
//        }

        if(Username == null)
        {

        }
        EditResult =editRepo.EditProfile(Username.value, Bio.value, ProfilePhoto.value)

    }
}
