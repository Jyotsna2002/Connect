package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.model.SearchProfileDataClassItem
import kotlinx.coroutines.launch

class SearchProfileViewModel : ViewModel() {


    private var SearchProfileResult: MutableLiveData<Response<List<SearchProfileDataClassItem>>> = MutableLiveData()
    val searchProfilResult: LiveData<Response<List<SearchProfileDataClassItem>>>
        get() = SearchProfileResult

    fun submitotherprofile(search: String,context: Context) = viewModelScope.launch {
        Log.i("searchText", "onActivityResult: ${search}")
        SearchProfileResult= SearchProfileRepo().SearchProfile(search,context)
    }
}