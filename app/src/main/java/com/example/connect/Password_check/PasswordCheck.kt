package com.example.connect.Password_check

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.connect.Network.ServiceBuilder1
import com.example.connect.model.AccessTkn
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


fun validPass(password: String): String? {
    return when {
        password.length < 8 -> {
            "Password must contains 8 Characters"
        }
        !password.matches(".*[A-Z].*".toRegex()) && (!password.matches(".*[\$#%@&*/+_=?^!].*".toRegex())) -> {
            "Must contain 1 Special character and 1 upper case character (\$#%@&*/+_=?^!)"
        }
        !password.matches(".*[a-z].*".toRegex()) -> {
            "Must contain 1 Lower case character"
        }
        !password.matches(".*[\$#%@&*/+_=?^!].*".toRegex()) -> {
            "Must contain 1 Special character (\$#%@&*/+_=?^!)"
        }
        !password.matches(".*[A-Z].*".toRegex()) -> {
            "Must contain 1 upper case character"
        }
        else -> null
    }
}


suspend fun generateToken(token: String, refToken: String, context: Context)
{
       Log.w("GENERATE TOKEN access", "NEW ACCESS TOKEN : $token")
    ServiceBuilder1.buildService(token).generateToken(
        AccessTkn(
            refresh = refToken
        )
    )
        .enqueue(object : Callback<AccessTkn?> {
            override fun onResponse(call: Call<AccessTkn?>, response: Response<AccessTkn?>) {

                   if(response.isSuccessful) {
                        val newToken = response.body()?.access.toString()
                       GlobalScope.launch {
                            Datastore(context).saveUserDetails(
                                Datastore.ACCESS_TOKEN_KEY,
                                newToken)

                            Log.w("NEW TOKEN", "NEW ACCESS TOKEN : $newToken")

                        }

                    }
            }

            override fun onFailure(call: Call<AccessTkn?>, t: Throwable) {
                Toast.makeText(context, " Failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
}
