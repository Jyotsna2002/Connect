package com.example.connect.View_model

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.CreateStory.Companion.empty
import com.example.connect.CreateStory.Companion.multiple
import com.example.connect.Repository.CreateStoryRepo
import com.example.connect.Password_check.Response
import com.example.connect.model.CreateStoryDataClass
import kotlinx.coroutines.launch


class CreateStoryViewModel : ViewModel() {

    var imageUrl = MutableLiveData<List<String>>()
    var videoUrl = MutableLiveData<List<String>>()

    private var CreateStoryResult: MutableLiveData<Response<CreateStoryDataClass>> =
        MutableLiveData()
    val createStoryResult: LiveData<Response<CreateStoryDataClass>>
        get() = CreateStoryResult

    fun submitDataCreateStory(image: List<String>,context:Context) = viewModelScope.launch {
        if (empty == 0) {
            imageUrl.value = emptyList()
        }


        if (multiple == 1) {
            CreateStoryResult = CreateStoryRepo().CreateStory(
                image,
                emptyList(), context
            )
        } else
            if (multiple == 0) {
                CreateStoryResult = CreateStoryRepo().CreateStory(
                    imageUrl.value,
                    emptyList(),context
                )
            }
    }
}
