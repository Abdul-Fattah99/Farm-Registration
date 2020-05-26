package com.agromall.farmregistration.ui.farmRegFragments

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.agromall.farmregistration.R
import com.agromall.farmregistration.data.Farmer
import kotlinx.android.synthetic.main.recycler_view_farm_list.view.*


class FarmListAdapter : RecyclerView.Adapter<FarmListAdapter.FarmListViewModel>() {

    private var farmers = mutableListOf<Farmer>()
    var listener: RecyclerViewClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FarmListViewModel(
        LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_farm_list, parent, false)
    )

    override fun getItemCount() = farmers.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FarmListViewModel, position: Int) {
        holder.view.text_view_name.text = farmers[position].name
        holder.view.text_view_city_votes.text =
            "${farmers[position].city} | Votes : ${farmers[position].votes}"
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, farmers[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, farmers[position])
        }
    }

    fun setFarmers(farmers: List<Farmer>) {
        this.farmers = farmers as MutableList<Farmer>
        notifyDataSetChanged()
    }

    fun addFarmer(farmer: Farmer) {
        if (!this.farmers.contains(farmer)) {
            this.farmers.add(farmer)
        } else {
            val index = this.farmers.indexOf(farmer)
            if (farmer.isDeleted) {
                this.farmers.removeAt(index)
            } else {
                this.farmers[index] = farmer
            }
        }
        notifyDataSetChanged()
    }

    class FarmListViewModel(val view: View) : RecyclerView.ViewHolder(view)
}