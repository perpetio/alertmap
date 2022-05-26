package com.perpetio.alertapp.fragments

import android.os.Bundle
import android.view.View
import com.perpetio.alertapp.databinding.FragmentSelectTerritotyBinding

class SelectTerritoryFragment : BaseFragment<FragmentSelectTerritotyBinding>() {

    override fun getViewBinding(): FragmentSelectTerritotyBinding {
        return FragmentSelectTerritotyBinding.inflate(layoutInflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setListeners()
    }

    private fun setListeners() {
        binding.apply {
            btnBack.setOnClickListener {
                goTo(SelectTerritoryFragmentDirections.toMainFragment())
            }
        }
    }
}