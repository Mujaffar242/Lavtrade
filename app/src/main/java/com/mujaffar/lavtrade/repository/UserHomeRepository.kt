package com.mujaffar.lavtrade.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.firebase.database.*
import com.mujaffar.currencyconverter.network.GoogleSheetApi
import com.mujaffar.lavtrade.MainApplication
import com.mujaffar.lavtrade.admin_module.models.BuySellModel
import com.mujaffar.lavtrade.models.FirebaseDataModel
import com.mujaffar.lavtrade.models.SheetPostModel
import com.mujaffar.lavtrade.network.SheetDataResponceModel
import com.mujaffar.lavtrade.network.SheetsExample
import com.mujaffar.lavtrade.user_module.ui.activities.UserHomeActivity
import com.mujaffar.lavtrade.utils.*
import com.mujaffar.medremind.database.BuySellDatabase
import com.mujaffar.medremind.database.DatabaseBuySellModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList


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
        val data =   GoogleSheetApi.retrofitServiceWithoutAuth.getGoogleSheetData("1IVJPVZW3XWtTOEJWFE5pDs-AoxcFvHikbIgOatYMGz4",userName,"A:J","AIzaSyCGXe3AW-Z33ujqd99IIGLmeX0IOCeVzBQ").await()

            googleSheetDataResponceModel.postValue(data)
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

        val sharedPreferences = SharedPrefHelper(context)


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


                val credentials =
                    GoogleCredential.fromStream(context.resources.assets.open("mujaffar-4d858-e3add9560dd6.json"))
                        .createScoped(Collections.singletonList("https://www.googleapis.com/auth/spreadsheets"))
                //  credentials.refreshIfExpired()
                credentials.refreshToken()
                val accessToken = credentials.accessToken
                Log.e("log_data", "Token :" + accessToken)
                sharedPreferences.insertStringToSharedPrefrences(Appconstants.AUTHTOKEN,accessToken)


                GoogleSheetApi.retrofitService.postDataTOGoogleSheet("1IVJPVZW3XWtTOEJWFE5pDs-AoxcFvHikbIgOatYMGz4","Sheet1","A:E","USER_ENTERED",sheetPostModel).await()

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

