package com.example.connect.Repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.Password_check.Datastore
import com.example.connect.Password_check.Response
import com.example.connect.Password_check.generateToken
import com.example.connect.model.EditProfileDataClass
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback

class EditProfileRepo {
    private val EditLiveData= MutableLiveData<Response<ResponseBody>>()
   suspend fun EditProfile(Username:String?,Bio:String?,Profile:String?,Private:Boolean?,context:Context): MutableLiveData<Response<ResponseBody>> {
        Log.i("Profile", "Respone $Username$Profile$Bio$Private")
        val token = Datastore(context).getUserDetails(Datastore.ACCESS_TOKEN_KEY)
        val call= ServiceBuilder1.buildService(token).editProfile(
            EditProfileDataClass(
                username = Username,
                bio = Bio,
                profile_photo=Profile,
                is_private = Private
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
                    EditLiveData.postValue(Response.Error("Username can not be empty"))
                }
                else if( response.code() == 406){
                    GlobalScope.launch {
                        generateToken(
                            token!!,
                            Datastore(context).getUserDetails(
                                Datastore.REF_TOKEN_KEY
                            )!!, context
                        )
                        EditProfile(Username,Bio,Profile,Private,context)
                    }
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