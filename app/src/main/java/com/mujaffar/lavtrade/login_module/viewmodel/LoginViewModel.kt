package com.mujaffar.lavtrade.login_module.viewmodel

import android.app.Application
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.*
import com.mujaffar.currencyconverter.repository.LoginRepository
import com.mujaffar.lavtrade.login_module.AuthListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/*
*view model for handling login events
*
* */

class LoginViewModel (application: Application): AndroidViewModel(application) {

    public var email = MutableLiveData<String>()
    public var password = MutableLiveData<String>()

    var showValiationToast = MutableLiveData<Boolean>()

    var _isLoginSuccess = MutableLiveData<Boolean>()

    //for show loading spinner
    var showLoadingProgressBar=MutableLiveData<Boolean>()

    //for redirect admin to admin login page
    private var _redirectToAdminHome = MutableLiveData<Boolean>()

    //for redirect user to user login page
    private var _redirectToUserHomePage = MutableLiveData<Boolean>()


    val redirectToAdminHome: LiveData<Boolean>
        get() = _redirectToAdminHome

    val redirectToUserHome: LiveData<Boolean>
        get() = _redirectToUserHomePage


    //for check if respose from firebase is success or not
    val isLoginSuccess: MutableLiveData<Boolean>
        get() = _isLoginSuccess

    val loginRepository: LoginRepository = LoginRepository(application)




    fun checkValidationAndDoLogin() {
        if (isValid()) {
            showValiationToast.value = false
            doLogin()
        } else {
            showValiationToast.value = true
        }
    }

    fun doLogin() {
        showLoadingProgressBar.value=true
        // loginRepository.getLogin(email?.value,password?.value)
        viewModelScope.launch {
            loginRepository.getLogin(email.value,password.value)
        }
    }

    /*
    * check if user name and password is valid
    * */
    fun isValid(): Boolean {
        var isValid: Boolean = true

        if (email.value == null || email.value == "") {
            isValid = false
        } else if (password.value == null || password.value == "") {
            isValid = false
        }
        return isValid
    }




    /*
   *  redirect to home admin and user
   * */
    fun redirectToAdmin() {
        _redirectToAdminHome.value = true
    }

    /*
    * reset redirect to home
    * */
    fun redirectToHome() {
        _redirectToUserHomePage.value = true
    }


    /*
    *
    * */
    fun makeRedirectHome()
    {
        if(email.value.toString().contains("admin"))
        {
            redirectToAdmin()
        }
        else{
            redirectToHome()
        }
    }


    /*
    * reset redirect to home admin and user
    * */
    fun resetRedirectToAdmin() {
        _redirectToAdminHome.value = false
    }

    /*
    * reset redirect to home
    * */
    fun resetRedirectToHome() {
        _redirectToUserHomePage.value = false
    }


    /*
  * for show loading spinner
  * */
    fun showLoadingSpinner()
    {
        showLoadingProgressBar.value=true;
    }


    /*
   * for hide loading spinner
   * */
    fun hideLoadingSpinner()
    {
        showLoadingProgressBar.value=false;
    }


}