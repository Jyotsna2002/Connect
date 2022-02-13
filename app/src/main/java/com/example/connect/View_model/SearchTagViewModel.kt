package com.example.connect.View_model

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.Password_check.Response
import com.example.connect.Repository.SearchTagRepo
import com.example.connect.model.SearchTagDataClass
import kotlinx.coroutines.launch

class SearchTagViewModel : ViewModel() {


    private var SearchTagResult: MutableLiveData<Response<List<SearchTagDataClass>>> = MutableLiveData()
    val searchTagResult: LiveData<Response<List<SearchTagDataClass>>>
        get() = SearchTagResult

    fun submitotherprofile(search: String,context: Context) = viewModelScope.launch {
        Log.i("searchText", "onActivityResult: ${search}")
        SearchTagResult= SearchTagRepo().SearchTag(search,context)
    }
}