package com.example.connect.Repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ApiInterface
import com.example.connect.model.EditProfileDataClass
import com.example.connect.model.SearchProfileDataClassItem
import com.example.connect.model.SearchTagDataClass
import com.example.connect.model.ShowFollowRequestDataClass
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class EditProfileRepo(private val service: ApiInterface) {
    private val EditLiveData= MutableLiveData<Response<ResponseBody>>()
    fun EditProfile(Username:String?,Bio:String?,Profile:String?): MutableLiveData<Response<ResponseBody>> {
        Log.i("Profile", "Respone $Username$Profile$Bio")
        val call=service.editProfile(
            EditProfileDataClass(
               username = Username,
                bio = Bio,
                profile_photo=Profile
            )
        )
        EditLiveData.postValue(Response.Loading())
        call.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: retrofit2.Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()!!
                    EditLiveData.postValue(Response.Success(responseBody))

                    Log.i("SearchTag", "onActivityResult:$responseBody")
                }
                else if(response.code()==400)
                {
                    EditLiveData.postValue(Response.Error("Username an not be empty"))
                }
                else {
                    EditLiveData.postValue(Response.Error(response.message()))

                    Log.i("SearchTag", "onActivityResult:"+response.code() )
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                EditLiveData.postValue(Response.Error("Something went wrong ${t.message}"))
                Log.i("SearchTag", "onActivityResult: failed" )
            }
        })
        return EditLiveData
    }
}