package com.mujaffar.currencyconverter.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GetTokenResult
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.SharedPrefHelper


/*
* data source for provide data to our ui layer
* */
class LoginRepository(context: Context) {

    private val firebaseAuth = FirebaseAuth.getInstance()

        var loginSuccess=MutableLiveData<Boolean>()

    val sharedPreferences=SharedPrefHelper(context)

    var errorFromFireBase=MutableLiveData<String>()


     fun getLogin(email: String?, password: String?) {

        firebaseAuth.signInWithEmailAndPassword(email.toString(), password.toString())
            .addOnCompleteListener() { task ->
                    // Sign in success, update UI with the signed-in user's information

                if(task.isSuccessful)
                {
                    task.result?.user?.getIdToken(true)
                        ?.addOnCompleteListener(OnCompleteListener<GetTokenResult> { task ->
                            if (task.isSuccessful) {
                                val token = task.result!!.token
                                Log.e("token2",token.toString())
                                sharedPreferences.insertBooleanToSharedPrefrences(Appconstants.IS_LOGIN,task.isSuccessful)
                                sharedPreferences.insertStringToSharedPrefrences(Appconstants.USERNAME,email.toString())

                                loginSuccess.value=true

                                // 'token' is not a Google Access Token
                            }
                        })

                }

            }

    }

}