package com.example.nfk_project.ui.Floor_1

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nfk_project.R
import com.example.nfk_project.ui.floor_3.Floor3Fragment
import com.example.nfk_project.ui.floor_3.Floor3ViewModel

class Floor1Fragment : Fragment() {

    companion object {
        fun newInstance() = Floor1Fragment()
    }

    private lateinit var viewModel: Floor1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.floor1_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Floor1ViewModel::class.java)
        // TODO: Use the ViewModel
    }
}