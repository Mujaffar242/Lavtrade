package com.mujaffar.lavtrade.admin_module.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mujaffar.lavtrade.R
import com.mujaffar.lavtrade.databinding.UserSummeryItemBinding
import com.mujaffar.lavtrade.utils.Symbole


/**
 * RecyclerView Adapter show user buy and sell list
 */

class SummeryListAdapter() :
    RecyclerView.Adapter<SummeryViewHolder>() {


    var summeryList: List<List<String>> = emptyList()
        set(value) {
            field = value
            // For an extra challenge, update this to use the paging library.

            // Notify any registered observers that the data set has changed. This will cause every
            // element in our RecyclerView to be invalidated.
            notifyDataSetChanged()
        }

    /**
     * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
     * an item.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SummeryViewHolder {
        return SummeryViewHolder.from(parent)
    }


    /**
     * Called by RecyclerView to display the data at the specified position. This method should
     * update the contents of the {@link ViewHolder#itemView} to reflect the item at the given
     * position.
     */
    override fun onBindViewHolder(holder: SummeryViewHolder, position: Int) {
        holder.viewDataBinding.also {
            it.sheetRow=summeryList.get(position)
            it.symbole= Symbole()
        }

        holder.bind()


    }

    override fun getItemCount(): Int {
      return  summeryList.size
    }


}


/**
 * ViewHolder for buy cell items. All work is done by data binding.
 */
class SummeryViewHolder(val viewDataBinding: UserSummeryItemBinding) :
    RecyclerView.ViewHolder(viewDataBinding.root) {
    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_summery_item

        public fun from(parent: ViewGroup): SummeryViewHolder {
            val withDataBinding: UserSummeryItemBinding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                LAYOUT,
                parent,
                false
            )
            return SummeryViewHolder(withDataBinding)
        }


    }


    public fun bind(

    ) {

    }

}



