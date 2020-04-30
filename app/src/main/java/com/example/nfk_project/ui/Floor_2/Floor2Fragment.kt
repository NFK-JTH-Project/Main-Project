package com.example.nfk_project.ui.Floor_2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.nfk_project.R

class Floor2Fragment : Fragment() {

    private lateinit var floor2ViewModel: Floor2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        floor2ViewModel =
            ViewModelProviders.of(this).get(Floor2ViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_floor_2, container, false)
        val textView: TextView = root.findViewById(R.id.text_floor_2)
        floor2ViewModel.text.observe(this, Observer {
            textView.text = it
        })
        return root
    }
}