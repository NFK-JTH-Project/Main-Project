package com.example.nfk_project.ui.floor_3

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.nfk_project.R

class Floor3Fragment : Fragment() {

    companion object {
        fun newInstance() = Floor3Fragment()
    }

    private lateinit var viewModel: Floor3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.floor3_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Floor3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
