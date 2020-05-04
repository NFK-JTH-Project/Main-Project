package com.example.nfk_project.ui.floor_0

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nfk_project.R

class Floor0Fragment : Fragment() {

    companion object {
        fun newInstance() = Floor0Fragment()
    }

    private lateinit var viewModel: Floor0ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.floor0_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Floor0ViewModel::class.java)
        // TODO: Use the ViewModel
    }
}