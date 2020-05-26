package com.agromall.farmregistration.ui.farmRegFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.agromall.farmregistration.R
import com.agromall.farmregistration.data.Farmer
import kotlinx.android.synthetic.main.fragment_edit_farm_info.*


class EditFarmInfoFragment(
    private val farmer: Farmer
) : DialogFragment() {

    private lateinit var viewModel: FarmListViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProviders.of(this).get(FarmListViewModel::class.java)
        return inflater.inflate(R.layout.fragment_edit_farm_info, container, false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_TITLE, android.R.style.Theme_DeviceDefault_Light_Dialog_MinWidth)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        edit_text_name.setText(farmer.name)

        viewModel.result.observe(viewLifecycleOwner, Observer {
            val message = if (it == null) {
                getString(R.string.farm_details_added)
            } else {
                getString(R.string.error, it.message)
            }
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            dismiss()
        })

        button_add.setOnClickListener {
            val name = edit_text_name.text.toString().trim()
            if (name.isEmpty()) {
                input_layout_name.error = getString(R.string.error_field_required)
                return@setOnClickListener
            }
            farmer.name = name
            viewModel.updateFarmer(farmer)
        }
    }
}