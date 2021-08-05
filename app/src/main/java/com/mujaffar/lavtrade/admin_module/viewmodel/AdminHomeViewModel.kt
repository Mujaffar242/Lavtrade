package com.mujaffar.lavtrade.admin_module.viewmodel

import android.app.Application
import android.app.Notification
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.mujaffar.lavtrade.models.NotificationData
import com.mujaffar.lavtrade.network.NotificationBody
import com.mujaffar.lavtrade.network.NotificationRequestModel
import com.mujaffar.lavtrade.repository.SendFcmRepository
import com.mujaffar.lavtrade.utils.UtilityFaction
import kotlinx.coroutines.launch
import java.util.*

class AdminHomeViewModel(application: Application) : AndroidViewModel(application) {


    var shareName = MutableLiveData<String>()

    var command = MutableLiveData<String>()

    var validationString = MutableLiveData<String>()


    val sendFcmRepository = SendFcmRepository()

    private val database = Firebase.database.reference

    init {
        validationString.value=""
    }


    /*
    * add notication data to firebase real time database
    * */
    fun writeNewNotification() {
        /*   val notification = NotificationData(shareName.value.toString(), command.value.toString())
           database.child("notifications").child(UtilityFaction.getCurrentDateString()).child(Calendar.getInstance().timeInMillis.toString()).setValue(notification)*/

        if(isValid().equals(""))
        sendFcmMessage()
    }


    fun sendFcmMessage() {
        var notificationRequestModel = NotificationRequestModel(
            "/topics/AdminMessage",
            NotificationBody(command.value.toString(), shareName.value.toString())
        )
        viewModelScope.launch {
            sendFcmRepository.sendFcm(notificationRequestModel)
        }
    }

    /*
  * check if user name and password is valid
  * */
    fun isValid() :String{
        var isValid: String = ""

        if (shareName.value == null || shareName.value == "") {
            isValid = "Enter share name"
        } else if (command.value == null || command.value == "") {
            isValid = "Select buy or sell"
        } else {
            isValid = ""
        }

        validationString.value=isValid
        return isValid

    }

}