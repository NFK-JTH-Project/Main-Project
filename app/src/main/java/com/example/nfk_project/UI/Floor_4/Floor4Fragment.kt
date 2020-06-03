package com.example.nfk_project.UI.Floor_4

/* *** Programmed by Rasmus Svanberg, 2020 *** */

import android.content.res.Configuration
import android.os.Bundle
import android.transition.ChangeBounds
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.CheckBox
import android.widget.TableLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.nfk_project.Helpers.MapKeyword
import com.example.nfk_project.MapCreator.bitmapRepository
import com.example.nfk_project.MapCreator.mapCreator
import com.example.nfk_project.R
import com.github.chrisbanes.photoview.PhotoView

const val LANDSCAPE_MAX_SCALE = 5.0F
const val PORTRAIT_MAX_SCALE = 6.0F

class Floor4Fragment : Fragment() {

    //region Class-scoped variables
    var MAX_SCALE: Float = 0F
    var MIN_SCALE: Float = 1F
    //endregion

    //region Declare variables
    private lateinit var root: View
    private lateinit var map: PhotoView
    private lateinit var toilets: CheckBox
    private lateinit var stairs: CheckBox
    private lateinit var exits: CheckBox
    private lateinit var rooms: CheckBox
    private lateinit var checkboxLayout: TableLayout
    private lateinit var animRaise: Animation
    private lateinit var animHide: Animation
    private lateinit var trans: Transition
    //endregion

    private lateinit var mainLayout: ConstraintLayout

    companion object {
        fun newInstance() = Floor4Fragment()
    }

    private lateinit var viewModel: Floor4ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.floor4_fragment, container, false)

        //Sets the max-scale of the zoom to depend on device orientation
        MAX_SCALE =
            if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                LANDSCAPE_MAX_SCALE
            } else { PORTRAIT_MAX_SCALE }

        /*Initiates everything that is needed*/
        initAnimations()
        initView()
        initOnClickListeners()
        initMap()

        /* Enhances the UI by adding some animations on zoom in and zoom out */
        //Scalefactor: > 1 = Zoom in, < 1 = Zoom out
        var first = true
        map.setOnScaleChangeListener { scaleFactor, _, _ ->
            if (first && map.scale >= map.minimumScale && scaleFactor > 1) {
                TransitionManager.beginDelayedTransition(mainLayout, trans)
                checkboxLayout.startAnimation(animHide)
                checkboxLayout.visibility = GONE
                first = false
            } else if (!first && map.scale <= map.minimumScale && scaleFactor < 1) {
                TransitionManager.beginDelayedTransition(mainLayout, trans)
                checkboxLayout.visibility = VISIBLE
                checkboxLayout.startAnimation(animRaise)
                first = true
            }
        }

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Floor4ViewModel::class.java)
        // TODO: Use the ViewModel
    }

    private fun initView() {
        mainLayout = root.findViewById(R.id.constraintlayout)

        map = root.findViewById(R.id.imageView)
        toilets = root.findViewById(R.id.toiletscheckbox)
        stairs = root.findViewById(R.id.stairselevatorscheckbox)
        exits = root.findViewById(R.id.exitsentrancescheckbox)
        rooms = root.findViewById(R.id.roomscheckbox)
        checkboxLayout = root.findViewById(R.id.checkboxlayout)

        toilets.isChecked = bitmapRepository.getFloor()?.find { it.keyWord == MapKeyword.f4Wc }!!.isVisible
        stairs.isChecked = bitmapRepository.getFloor()?.find { it.keyWord == MapKeyword.f4Stairs }!!.isVisible
        exits.isChecked = bitmapRepository.getFloor()?.find { it.keyWord == MapKeyword.f4Exits }!!.isVisible
        rooms.isChecked = bitmapRepository.getFloor()?.find { it.keyWord == MapKeyword.f4Rooms }!!.isVisible
    }

    private fun initAnimations() {
        animRaise = AnimationUtils.loadAnimation(this.context, R.anim.anim_show_checkboxes)
        animRaise.fillAfter = true
        animHide = AnimationUtils.loadAnimation(this.context, R.anim.anim_hide_checkboxes)
        animHide.fillAfter = true

        trans = ChangeBounds()
        trans.duration = 100
    }

    private fun initMap() {
        map.maximumScale = MAX_SCALE
        map.minimumScale = MIN_SCALE
        map.scale = map.minimumScale

        updateMapOnScreen()
    }

    private fun initOnClickListeners() {
        toilets.setOnCheckedChangeListener { _, isChecked->
            onCheckboxClicked(MapKeyword.f4Wc, isChecked)
        }

        stairs.setOnCheckedChangeListener { _, isChecked->
            onCheckboxClicked(MapKeyword.f4Stairs, isChecked)
        }

        exits.setOnCheckedChangeListener { _, isChecked->
            onCheckboxClicked(MapKeyword.f4Exits, isChecked)
        }

        rooms.setOnCheckedChangeListener { _, isChecked->
            onCheckboxClicked(MapKeyword.f4Rooms, isChecked)
        }
    }

    private fun onCheckboxClicked(keyword: MapKeyword, checked: Boolean) {
        bitmapRepository.changeVisibilityByKeyword(keyword, checked)
        updateMapOnScreen()
    }

    private fun updateMapOnScreen() {
        mapCreator.createBitmap(bitmapRepository.getFloor()!!) { bitmap, _, _ ->
            map.setImageBitmap(bitmap)
        }
    }
}