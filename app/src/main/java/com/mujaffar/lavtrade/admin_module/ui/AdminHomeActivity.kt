package com.mujaffar.lavtrade.admin_module.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.messaging.FirebaseMessaging
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.admin_module.viewmodel.AdminHomeViewModel
import com.mujaffar.lavtrade.databinding.ActivityAdminHomeBinding
import com.mujaffar.lavtrade.login_module.viewmodel.LoginViewModel
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.UtilityFaction
import com.mujaffar.lavtrade.utils.createDialogue

class AdminHomeActivity : AppCompatActivity() {

    //for hold biding object
     lateinit var adminHomeBinding: ActivityAdminHomeBinding

    private lateinit var list: Array<String>

    lateinit var  adminHomeViewModel: AdminHomeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //init binding object
        adminHomeBinding=DataBindingUtil.setContentView(this,R.layout.activity_admin_home)

        adminHomeViewModel = ViewModelProviders.of(this).get(AdminHomeViewModel::class.java)

        adminHomeBinding.adminHomeViewModel = adminHomeViewModel


        list=arrayOf(getString(R.string.buy),getString(R.string.sell))

        //set adapter for buy cell dropdown
        // Sets the adapter of the RecyclerView
        adminHomeBinding.buysellAutoComplete.setAdapter(ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,list))


        adminHomeBinding.buysellAutoComplete.setOnClickListener {
            adminHomeBinding.buysellAutoComplete.showDropDown()
        }


        adminHomeViewModel.validationString.observe(this, Observer {
            if(!it.equals(""))
            {
                Toast.makeText(this,it,Toast.LENGTH_LONG).show()
            }
        })



        adminHomeBinding.sendNotificationButton.setOnClickListener {

            adminHomeViewModel.writeNewNotification(this)
        }



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.admin_home_menu, menu)
        return true
    }

    //and this to handle actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        return if (id == R.id.logout) {
            createDialogue(this, Appconstants.DialogueType.LOG_OUT,null)
            true
        } else super.onOptionsItemSelected(item)
    }



}