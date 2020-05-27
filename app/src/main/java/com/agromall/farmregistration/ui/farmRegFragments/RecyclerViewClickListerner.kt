package com.agromall.farmregistration.ui.farmRegFragments

import android.view.View
import com.agromall.farmregistration.data.Farmer

interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view: View, farmer: Farmer)
}