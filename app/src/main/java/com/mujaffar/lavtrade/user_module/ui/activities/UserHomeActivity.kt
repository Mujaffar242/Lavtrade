package com.mujaffar.lavtrade.user_module.ui.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.admin_module.ui.AdminHomeActivity
import com.mujaffar.lavtrade.admin_module.ui.adapter.BuySellListAdapter
import com.mujaffar.lavtrade.databinding.ActivityUserHomeBinding
import com.mujaffar.lavtrade.user_module.BuySellClickListner
import com.mujaffar.lavtrade.user_module.viewmodel.UserHomeviewModel
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.UtilityFaction
import com.mujaffar.lavtrade.utils.createDialogue
import com.mujaffar.medremind.database.DatabaseBuySellModel


class UserHomeActivity : AppCompatActivity(), BuySellClickListner {


    //for hold data binding reference
    lateinit var binding: ActivityUserHomeBinding;

    /**
     * RecyclerView Adapter
     */
     var viewModelAdapter: BuySellListAdapter? = null

    //for hold currency viewmodel
    lateinit var viewModel: UserHomeviewModel


    //variable for hold progress dialog
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)



        if(!UtilityFaction.isInternetAvalible(this))
        {
            createDialogue(this,Appconstants.DialogueType.NO_INTERNET,null)
        }



        //init binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_home)



        //subscribe admin message  topic to recive notification
        Firebase.messaging.subscribeToTopic("AdminMessage")


        //init viewmodel
        viewModelAdapter = BuySellListAdapter(this)

        //set adapter to recylerview
        binding.contactListRecyclerView.adapter = viewModelAdapter



        viewModel = ViewModelProviders.of(this).get(UserHomeviewModel::class.java)


        binding.lifecycleOwner = this
        //  viewModel= ViewModelProviders.of(this).get(ScheduleviewModel::class.java)

        binding.scheduleviewModel = viewModel


        //observe schedule live data based on different page type and add to adapter
        //if page type is contactlist
        viewModel.buycellList?.observe(this, Observer { buyselllist ->
            buyselllist?.apply {
                //viewModelAdapter?.contacts = contact as List<DatabaseContactModel>

                viewModelAdapter?.submitList(buyselllist as List<DatabaseBuySellModel>)


            }
        })



        binding.fabSummery.setOnClickListener {
            startActivity(
                Intent(
                    this, SummeryActivity::class.java
                )
            )
        }

        viewModel.buycellList?.observe(this, Observer { buycellList ->
            buycellList?.apply {
                //viewModelAdapter?.contacts = contact as List<DatabaseContactModel>


                hideShowNODataBasedOnList(buycellList)

            }
        })


        /*
        * observer the showLoadingProgressBar and based on this show loading spinner
        * */
        viewModel.showLoadingProgressBar.observe(this, Observer {
            if (it) {
                progressDialog.show()
            } else {
                progressDialog.dismiss()
            }
        })


        viewModel.showConfrimDialouge.observe(this, Observer {
            createDialogue(this, it,null)
            viewModelAdapter?.notifyDataSetChanged()

        })

    }


    /*
    * funcation for show no data text centre of screen if list is empaty
    * */
    private fun hideShowNODataBasedOnList(contact: List<DatabaseBuySellModel>) {
        if (contact.isEmpty()) {
            binding.noDataText.visibility = View.VISIBLE
        } else {
            binding.noDataText.visibility = View.GONE
        }
    }

    /*
    * for update buy and sell status on room database
    * */
    override fun onBuySellClick(databaseBuySellModel: DatabaseBuySellModel) {


        //if already buy or sell simplely return
        if (databaseBuySellModel.isByOrSell)
        {
            Toast.makeText(this,"You have already responded to this notification",Toast.LENGTH_LONG).show()
            return
        }

        //show dailoge based on command type
        if(databaseBuySellModel.command.equals("Buy"))
        {
            createDialogue(this,Appconstants.DialogueType.BUY_DIALOUGE,databaseBuySellModel)
        }
        else{
            createDialogue(this,Appconstants.DialogueType.SELL_DIALOUGE,databaseBuySellModel)
        }



        //otherwise show dialogue for confirm buy or sell
       // viewModel.changeCompleteStatus(databaseBuySellModel)


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.user_home_menu, menu)
        return true
    }

    //and this to handle actions
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id: Int = item.getItemId()
        return if (id == R.id.logout) {
            createDialogue(this,Appconstants.DialogueType.LOG_OUT,null)
            true
        }else if(id == R.id.call) {
            openDailerWithNumber()
            true
        }
            else super.onOptionsItemSelected(item)
    }



    fun openDailerWithNumber(){
        val intent = Intent(Intent.ACTION_DIAL)
        intent.data = Uri.parse("tel:+91 95165 92571")
        startActivity(intent)
    }


}