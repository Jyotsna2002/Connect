package com.example.connect.View_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Repository.Response
import com.example.connect.Repository.SearchProfileRepo
import com.example.connect.Repository.SearchTagRepo
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import kotlinx.coroutines.launch

class SearchTagViewModel(private val searchTagRepo: SearchTagRepo) : ViewModel() {


    private var SearchTagResult: MutableLiveData<Response<List<SearchTagDataClass>>> = MutableLiveData()
    val searchTagResult: LiveData<Response<List<SearchTagDataClass>>>
        get() = SearchTagResult

    fun submitotherprofile(search: String) = viewModelScope.launch {
        Log.i("searchText", "onActivityResult: ${search}")
        SearchTagResult= searchTagRepo.SearchTag(search)
    }
}