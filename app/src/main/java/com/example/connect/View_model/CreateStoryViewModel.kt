package com.example.connect.View_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.connect.CreateStory.Companion.empty
import com.example.connect.CreateStory.Companion.multiple
import com.example.connect.Repository.CreateStoryRepo
import com.example.connect.Repository.Response
import com.example.connect.model.CreateStoryDataClass
import kotlinx.coroutines.launch


class CreateStoryViewModel(private val createStoryRepo: CreateStoryRepo) : ViewModel() {

    var imageUrl = MutableLiveData<List<String>>()
    var videoUrl = MutableLiveData<List<String>>()

    private var CreateStoryResult: MutableLiveData<Response<CreateStoryDataClass>> =
        MutableLiveData()
    val createStoryResult: LiveData<Response<CreateStoryDataClass>>
        get() = CreateStoryResult

    fun submitDataCreateStory(image: List<String>) = viewModelScope.launch {
        if (empty == 0) {
            imageUrl.value = emptyList()
        }


        if (multiple == 1) {
            CreateStoryResult = createStoryRepo.CreateStory(
                image,
                emptyList()
            )
        } else
            if (multiple == 0) {
                CreateStoryResult = createStoryRepo.CreateStory(
                    imageUrl.value,
                    emptyList()
                )
            }
    }
}
