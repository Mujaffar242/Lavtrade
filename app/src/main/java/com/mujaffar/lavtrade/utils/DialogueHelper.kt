package com.mujaffar.lavtrade.utils

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.admin_module.viewmodel.AdminHomeViewModel
import com.mujaffar.lavtrade.databinding.CustomDailogueViewBinding

lateinit var adminHomeViewModel: AdminHomeViewModel

/*
   * create buy dialouge
   * */

fun createDialogue(context: Context,type:Int) {
    val builder = AlertDialog.Builder(context)

    val customDailogueViewBinding = CustomDailogueViewBinding.inflate(LayoutInflater.from(context))

    builder.setView(customDailogueViewBinding.root)


    val alertDialog = builder.create()

    /*hide and show contoles based on dailouge type*/
    if(type==Appconstants.DialogueType.BUY_DIALOUGE)
    {
        customDailogueViewBinding.checkedIcon.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.enter_the_number_of_share_bought))

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            alertDialog.dismiss()
            createDialogue(context,Appconstants.DialogueType.BUY_CONFIRM)
        }
    }
    else if(type==Appconstants.DialogueType.SELL_DIALOUGE)
    {
        customDailogueViewBinding.checkedIcon.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.confirm_that_you_have_sold))
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            alertDialog.dismiss()
            createDialogue(context,Appconstants.DialogueType.SELL_CONFIRM)
        }
    }
    else if(type==Appconstants.DialogueType.BUY_CONFIRM)
    {
        customDailogueViewBinding.title.setText(context.getString(R.string.success))
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.you_have_confirmed_buying))
        customDailogueViewBinding.confrimbutton.setText(context.getString(R.string.done))
        customDailogueViewBinding.cancelButton.visibility=View.GONE

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            alertDialog.dismiss()
        }

    }
    else if(type==Appconstants.DialogueType.SELL_CONFIRM)
    {
        customDailogueViewBinding.title.setText(context.getString(R.string.success))
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.confrim_selling_share))
        customDailogueViewBinding.confrimbutton.setText(context.getString(R.string.done))
        customDailogueViewBinding.cancelButton.visibility=View.GONE

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            alertDialog.dismiss()
        }
    }
    else if(type==Appconstants.DialogueType.LOG_OUT)
    {
        customDailogueViewBinding.checkedIcon.visibility=View.GONE
        customDailogueViewBinding.title.setText("Logout?")
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.confirm_logout))
        customDailogueViewBinding.confrimbutton.setText(context.getString(R.string.logout_button_text))

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            UtilityFaction.doLogout(context)
            (context as Activity).finish()
           // finish()
            alertDialog.dismiss()
        }
    }

    else if(type==Appconstants.DialogueType.SEND_NOTIFICATION)
    {
        customDailogueViewBinding.checkedIcon.visibility=View.GONE
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.send_notification_to_every_one))
        customDailogueViewBinding.confrimbutton.setText(context.getString(R.string.send))

        customDailogueViewBinding.confrimbutton.setOnClickListener {

        }
    }
    else if(type==Appconstants.DialogueType.CONFRIM_NOTIFICATION)
    {
        customDailogueViewBinding.title.setText(context.getString(R.string.success))
        customDailogueViewBinding.editTextNumberOfShare.visibility=View.GONE
        customDailogueViewBinding.subTitle.setText(context.getString(R.string.notifiation_sent_to_everyone))
        customDailogueViewBinding.confrimbutton.setText(context.getString(R.string.done))
        customDailogueViewBinding.cancelButton.visibility=View.GONE

        customDailogueViewBinding.confrimbutton.setOnClickListener {
            alertDialog.dismiss()
        }
    }




    alertDialog.show()

    customDailogueViewBinding.cancelButton.setOnClickListener() {
        alertDialog.dismiss()
    }

}