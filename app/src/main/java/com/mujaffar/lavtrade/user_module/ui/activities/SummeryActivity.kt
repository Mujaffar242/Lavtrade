package com.mujaffar.lavtrade.user_module.ui.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.admin_module.ui.adapter.BuySellListAdapter
import com.mujaffar.lavtrade.admin_module.ui.adapter.SummeryListAdapter
import com.mujaffar.lavtrade.databinding.ActivitySummeryBinding
import com.mujaffar.lavtrade.databinding.ActivityUserHomeBinding
import com.mujaffar.lavtrade.user_module.viewmodel.UserHomeviewModel
import com.mujaffar.lavtrade.user_module.viewmodel.UserSummeryviewModel
import com.mujaffar.medremind.database.DatabaseBuySellModel

class SummeryActivity : AppCompatActivity() {


    //for hold data binding reference
    lateinit var binding: ActivitySummeryBinding;

    /**
     * RecyclerView Adapter
     */
    private var viewModelAdapter: SummeryListAdapter? = null

    //for hold currency viewmodel
    lateinit var viewModel: UserSummeryviewModel


    //variable for hold progress dialog
    lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        //init progress dialog
        progressDialog = ProgressDialog(this)
        progressDialog.setCancelable(false)


        //init binding
        binding = DataBindingUtil.setContentView(this, R.layout.activity_summery)



        setTitle(getString(R.string.summery))

        //for show back button on actionbar
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        //init viewmodel
        viewModelAdapter = SummeryListAdapter()

        //set adapter to recylerview
        binding.recylerViewSummery.adapter = viewModelAdapter



        viewModel = ViewModelProviders.of(this).get(UserSummeryviewModel::class.java)


        binding.lifecycleOwner = this
        //  viewModel= ViewModelProviders.of(this).get(ScheduleviewModel::class.java)

        binding.userSummeryViewModel = viewModel

        viewModel.showLoadingSpinner()
        //observe schedule live data based on different page type and add to adapter
        //if page type is contactlist
        viewModel.summeryList?.observe(this, Observer { sumerylist ->
            sumerylist?.apply {
                //viewModelAdapter?.contacts = contact as List<DatabaseContactModel>

                var list:ArrayList<ArrayList<String>>

                list=sumerylist.values as ArrayList<ArrayList<String>>


                list?.removeAt(0)

                viewModelAdapter?.summeryList=list
                viewModel.hideLoadingSpinner()
            }
        })


        viewModel.showLoadingProgressBar.observe(this, Observer {
            if(it)
            {
                progressDialog.show()
            }
            else{
                progressDialog.dismiss()
            }
        })

    }

    // function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}