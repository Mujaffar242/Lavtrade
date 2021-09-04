package com.mujaffar.lavtrade

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.mujaffar.medremind.database.DatabaseBuySellModel


    /*
* binding adapter set instraction  text
* */
    @BindingAdapter("setInstructionText")
    fun TextView.setInstructionText(data:DatabaseBuySellModel)
    {
        if(data.command.equals("Sell"))
        {
            setText(R.string.sell_this_share)
        }
        else{
            setText(R.string.buy_this_share)
        }
    }


@BindingAdapter("setTextColor")
fun TextView.setTextColor(data:DatabaseBuySellModel)
{
    if(data.command.equals("Sell"))
    {
        setTextColor(resources.getColor(R.color.red))
    }
    else{
        setTextColor(resources.getColor(R.color.green))
    }
}

@BindingAdapter("setBackground")
fun ConstraintLayout.setbackground(data:DatabaseBuySellModel)
{
    if(data.command.equals("Sell"))
    {
        setBackgroundResource(R.drawable.home_user_sellbg)
    }
    else{
        setBackgroundResource(R.drawable.home_user_buybg)
    }
}


@BindingAdapter("setBackgroundForSummery")
fun ConstraintLayout.setBackgroundForSummery(type:String)
{
    if(type.equals("Overall Summary"))
    {
        setBackgroundResource(R.drawable.overallsummerybg)
    }
    else{
        setBackgroundResource(R.drawable.summerybg)
    }
}


@BindingAdapter("setOpicity")
fun View.setOpicity(data:DatabaseBuySellModel)
{
    if(data.isByOrSell)
    {
        background.alpha=178

    }
    else{
        background.alpha=255
    }
}