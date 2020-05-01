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

class Floor1Fragment : Fragment() {

    private lateinit var floor1ViewModel: Floor1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        floor1ViewModel =
            ViewModelProviders.of(this).get(Floor1ViewModel::class.java)
        val root = inflater.inflate(R.layout.floor1_fragment, container, false)
        val textView: TextView = root.findViewById(R.id.text_floor_1)
        floor1ViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}