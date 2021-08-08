package com.mujaffar.lavtrade.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.*
import com.mujaffar.currencyconverter.network.GoogleSheetApi
import com.mujaffar.lavtrade.MainApplication
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.models.FirebaseDataModel
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.network.SheetDataResponceModel
import com.mujaffar.lavtrade.network.SheetsExample
import com.mujaffar.lavtrade.user_module.ui.activities.UserHomeActivity
import com.mujaffar.lavtrade.utils.Appconstants
import com.mujaffar.lavtrade.utils.SpreadsheetSnippets
import com.mujaffar.lavtrade.utils.UtilityFaction
import com.mujaffar.lavtrade.utils.createDialogue
import com.mujaffar.medremind.database.BuySellDatabase
import com.mujaffar.medremind.database.DatabaseBuySellModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class UserHomeRepository(val database: BuySellDatabase) {



    /*
* get list of all buy sell that are store in database
*  */
    val allBuySellForToday: LiveData<List<DatabaseBuySellModel>> = database.buySellDao.getAllBuySellListForDay()


    /*
    * live data model for data from google sheet api
    * */
    var googleSheetDataResponceModel=MutableLiveData<SheetDataResponceModel>()



    /*
* function for get summery data from google sheet
* */
    suspend fun getDataFromGoogleSheet(userName:String) {

        withContext(Dispatchers.IO)
        {
        googleSheetDataResponceModel.value =   GoogleSheetApi.retrofitServiceWithoutAuth.getGoogleSheetData("15B-ce4CC7OBqieMwH4TLBzUzMHHC4qzt2UEfbCfaPnM",userName,"A:J","AIzaSyBa8lxt2dSjV5aw9RkU0uh38h6jYCI9mm8").await()
        }
    }




    /*
* function for update task complete status
* */
    suspend fun updateCompleteStatus(databaseBuySellModel: DatabaseBuySellModel,userName:String,context:Context)
    {
       /* withContext(Dispatchers.IO){
            databaseBuySellModel.isByOrSell = true
            database.buySellDao.update(databaseBuySellModel)
        }*/


        withContext(Dispatchers.IO)
        {
            val arraylist=ArrayList<ArrayList<String>>()

            val buysellarry=ArrayList<String>()

            buysellarry.add(UtilityFaction.getCurrentDateString())
            buysellarry.add(userName)
            buysellarry.add(databaseBuySellModel.command)
            buysellarry.add(databaseBuySellModel.shareName)
            buysellarry.add(databaseBuySellModel.numberOfShare.toString())

            arraylist.add(buysellarry)

           val sheetPostModel= SheetPostModel("Sheet1!A:E","ROWS",arraylist)




            try {
                GoogleSheetApi.retrofitService.postDataTOGoogleSheet("15B-ce4CC7OBqieMwH4TLBzUzMHHC4qzt2UEfbCfaPnM","Sheet1","A:E","USER_ENTERED",sheetPostModel).await()

                //mark this share action complete on local database
                databaseBuySellModel.isByOrSell=true
                database.buySellDao.update(databaseBuySellModel)

                (context as UserHomeActivity).viewModel.hideLoadingSpinner()


                //show the confrim dailoge based on command type
                if(databaseBuySellModel.command.equals("Buy"))
                {
                    createDialogue(context, Appconstants.DialogueType.BUY_CONFIRM,null)

                }
                else{
                    createDialogue(context, Appconstants.DialogueType.SELL_CONFIRM,null)

                }

            }
            catch (ex:Exception)
            {
                (context as UserHomeActivity).viewModel.hideLoadingSpinner()

            }



        }


    }




}

