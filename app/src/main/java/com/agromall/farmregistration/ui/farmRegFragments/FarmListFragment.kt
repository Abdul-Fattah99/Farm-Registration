package com.agromall.farmregistration.ui.farmRegFragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agromall.farmregistration.R
import android.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agromall.farmregistration.data.Farmer
import kotlinx.android.synthetic.main.fragment_all_farms.*


class FarmListFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: FarmListViewModel
    private val adapter = FarmListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FarmListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_all_farms, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_farmers.adapter = adapter

        viewModel.fetchFilteredFarmers(6)
//        viewModel.getRealtimeUpdates()

        viewModel.farmers.observe(viewLifecycleOwner, Observer {
            adapter.setFarmers(it)
        })

        viewModel.farmer.observe(viewLifecycleOwner, Observer {
            adapter.addFarmer(it)
        })

        button_add.setOnClickListener {
            AddFarmInfoFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, farmer: Farmer) {
        when (view.id) {
            R.id.button_edit -> {
                EditFarmInfoFragment(farmer).show(childFragmentManager, "")
            }
            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle(getString(R.string.delete_confirmation))
                    it.setPositiveButton(getString(R.string.yes)) { dialog, which ->
                        viewModel.deleteFarmer(farmer)
                    }
                }.create().show()
            }
        }
    }
}
