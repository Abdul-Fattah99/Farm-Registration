package com.agromall.farmregistration.ui.farmRegFragments



import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.agromall.farmregistration.R
import com.agromall.farmregistration.ui.loginFragments.ProfileFragmentDirections

import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_profile.*

//import android.R

class HomeFragment: Fragment() {

    private lateinit var viewModel: FarmListViewModel
    private val adapter = FarmListAdapter()
    private val farmers = FarmListFragment()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FarmListViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(com.agromall.farmregistration.R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.fetchFarmers()
        viewModel.fetchFilteredFarmers(6)
        viewModel.getRealtimeUpdates()


        profile_link.setOnClickListener {
           // val action = ProfileFragmentDirections.actionUpdatePassword()
            Navigation.findNavController(it).navigate(R.id.action_homeFragment_to_profileFragment)
        }




//        val total =  FarmListFragment()
//        total_farmers_number.text = total.

        var count = 0
        count == adapter.itemCount

        //total_farmers_number.text = viewModel.fetchFarmers()
    }
}
