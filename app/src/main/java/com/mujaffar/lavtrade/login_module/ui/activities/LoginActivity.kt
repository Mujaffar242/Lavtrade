package com.mujaffar.lavtrade.login_module.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.admin_module.ui.AdminHomeActivity
import com.mujaffar.lavtrade.databinding.ActivityLoginBinding
import com.mujaffar.lavtrade.login_module.viewmodel.LoginViewModel
import com.mujaffar.lavtrade.user_module.ui.activities.UserHomeActivity
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.SharedPrefHelper
import com.mujaffar.lavtrade.utils.UtilityFaction
import com.mujaffar.lavtrade.utils.createDialogue

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding

    lateinit var loginViewModel: LoginViewModel


    //variable for hold progress dialog
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if(!UtilityFaction.isInternetAvalible(this))
        {
            createDialogue(this,Appconstants.DialogueType.NO_INTERNET,null)
        }

        val sharedPrefHelper: SharedPrefHelper = SharedPrefHelper(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)


        binding.forgotPassword.setOnClickListener {
            openDailerWithNumber()
        }

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)

        binding.loginViewModel = loginViewModel

        supportActionBar?.hide()

        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)

        //get email for shared prefrences
        if (!sharedPrefHelper.getStringValueFromSharedPrefrences(Appconstants.USERNAME)
                .equals("")
        ) {
            loginViewModel.email.value =
                sharedPrefHelper.getStringValueFromSharedPrefrences(Appconstants.USERNAME)
            loginViewModel.makeRedirectHome()
        }




        loginViewModel.showValiationToast.observe(this, Observer {
            if (it)
                Toast.makeText(this, "Incorrect Username or Password", Toast.LENGTH_LONG).show()
        })


        /*
       * observer the showLoadingProgressBar and based on this show loading spinner
       * */
        loginViewModel.showLoadingProgressBar.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })



        loginViewModel.loginRepository.loginSuccess.observe(this, Observer {
            loginViewModel.showLoadingProgressBar.value = false
            if (it) {
                loginViewModel.makeRedirectHome()
            } else {
                Toast.makeText(
                    baseContext, "Incorrect Username or Password",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })



        loginViewModel.redirectToUserHome.observe(this, Observer {
            if (it) {
                startUserHome()
                finish()
            }
        })


        loginViewModel.redirectToAdminHome.observe(this, Observer {
            if (it) {
                startAdminHome()
                finish()
            }
        })
    }


    /*
    * start admin home activity
    * */
    fun startAdminHome() {
        startActivity(
            Intent(
                this, AdminHomeActivity::class.java
            )
        )

        loginViewModel.resetRedirectToAdmin()
    }


    /*
    * start user home activity
    * */
    fun startUserHome() {
        startActivity(
            Intent(
                this, UserHomeActivity::class.java
            )
        )

        loginViewModel.resetRedirectToHome()
    }


    fun openDailerWithNumber(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:+91 95165 92571")
        startActivity(intent)
    }
}