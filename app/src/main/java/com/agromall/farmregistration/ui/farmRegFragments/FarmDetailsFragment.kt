package com.agromall.farmregistration.ui.farmRegFragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.agromall.farmregistration.R
import com.agromall.farmregistration.data.Farmer
import kotlinx.android.synthetic.main.fragment_farm_details.*


class FarmDetailsFragment(
    private val farmer: Farmer
) : DialogFragment() {

    private lateinit var viewModel: FarmListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FarmListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_farm_details, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nav_to_map_view.setOnClickListener{
            MapsFragment(farmer)
                .show(childFragmentManager, "")
        }

        farmer_name.text = farmer.name
        farmer_dob.text = farmer.dob
        farmer_phone_number.text = farmer.phoneNumber
        farm_name.text = farmer.farmName
        farm_address.text = farmer.location
        farm_size.text = farmer.farmSize

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.farm_details_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

    }
}
