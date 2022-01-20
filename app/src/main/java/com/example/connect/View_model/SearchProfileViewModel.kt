package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.Response
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.model.SearchProfileDataClassItem
import kotlinx.coroutines.launch

class SearchProfileViewModel(private val searchProfileRepo: SearchProfileRepo) : ViewModel() {


    private var SearchProfileResult: MutableLiveData<Response<List<SearchProfileDataClassItem>>> = MutableLiveData()
    val searchProfilResult: LiveData<Response<List<SearchProfileDataClassItem>>>
        get() = SearchProfileResult

    fun submitotherprofile(search: String) = viewModelScope.launch {
        Log.i("searchText", "onActivityResult: ${search}")
        SearchProfileResult= searchProfileRepo.SearchProfile(search)
    }
}