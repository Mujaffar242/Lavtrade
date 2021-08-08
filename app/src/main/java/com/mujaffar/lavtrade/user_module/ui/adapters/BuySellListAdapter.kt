package com.mujaffar.lavtrade.admin_module.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.user_module.BuySellClickListner
import com.mujaffar.lavtrade.databinding.UserHomeItemBinding
import com.mujaffar.medremind.database.DatabaseBuySellModel


/**
 * RecyclerView Adapter show user buy and sell list
 */

class BuySellListAdapter(private val buySellClickListner: BuySellClickListner) :
    ListAdapter<DatabaseBuySellModel, BuySellViewHolder>(ContactDiffCallback()) {

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BuySellViewHolder {
        return BuySellViewHolder.from(parent)
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: BuySellViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.buySellModel = getItem(position)
            it.buySellClickListner = buySellClickListner
        }

        holder.bind()


    }


}


/**
 * ViewHolder for buy cell items. All work is done by data binding.
 */
class BuySellViewHolder private constructor(val viewDataBinding: UserHomeItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_home_item

        public fun from(parent: ViewGroup): BuySellViewHolder {
            val withDataBinding: UserHomeItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                LAYOUT,
                parent,
                false
            )
            return BuySellViewHolder(withDataBinding)
        }


    }


    public fun bind(

    ) {

    }

}


class ContactDiffCallback :
    DiffUtil.ItemCallback<DatabaseBuySellModel>() {
    override fun areItemsTheSame(
        oldItem: DatabaseBuySellModel,
        newItem: DatabaseBuySellModel
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: DatabaseBuySellModel,
        newItem: DatabaseBuySellModel
    ): Boolean {
        return oldItem == newItem;
    }
}
