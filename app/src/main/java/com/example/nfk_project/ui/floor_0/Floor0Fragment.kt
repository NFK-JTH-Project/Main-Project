package com.example.nfk_project.ui.floor_0

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nfk_project.R

class Floor0Fragment : Fragment() {

    private lateinit var floor0ViewModel: Floor0ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        floor0ViewModel =
            ViewModelProviders.of(this).get(Floor0ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_floor_0, container, false)
        val textView: TextView = root.findViewById(R.id.text_floor_0)
        floor0ViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}