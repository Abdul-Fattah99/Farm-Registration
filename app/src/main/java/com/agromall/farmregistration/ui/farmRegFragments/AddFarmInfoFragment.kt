package com.agromall.farmregistration.ui.farmRegFragments


import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.agromall.farmregistration.R
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agromall.farmregistration.data.Farmer
import kotlinx.android.synthetic.main.fragment_add_farm_info.*


class AddFarmInfoFragment : DialogFragment() {

    private lateinit var viewModel: FarmListViewModel
    private var REQUEST_IMAGE_CAPTURE = 100

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FarmListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_add_farm_info, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.farm_details_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })
        farmer_image.setOnClickListener{

        }


        button_add.setOnClickListener {
            //val image = farmer_image.setImageBitmap()
            val name = edit_text_name.text.toString().trim()
            val dob = edit_text_dob.text.toString().trim()
            val phoneNumber = edit_text_phoneNumber.text.toString().trim()
            val farmName = edit_text_farmName.text.toString().trim()
            val address = edit_text_Address.text.toString().trim()
            val farmSize = edit_text_farmSize.text.toString().trim()

            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }

            val farmer = Farmer()

            farmer.name = name
            farmer.dob = dob
            farmer.phoneNumber = phoneNumber
            farmer.farmName = farmName
            farmer.location = address
            farmer.farmSize = farmSize

            viewModel.addFarmer(farmer)
        }
    }


}