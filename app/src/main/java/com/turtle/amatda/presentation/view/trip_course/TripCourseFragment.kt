package com.turtle.amatda.presentation.view.trip_course

import android.util.Log
import androidx.navigation.fragment.navArgs
import com.google.android.gms.common.api.Status
import com.google.android.gms.tasks.Task
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripCourseBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import javax.inject.Inject

class TripCourseFragment :
    BaseFragment<TripCourseViewModel, FragmentTripCourseBinding>(R.layout.fragment_trip_course) {

    private val args: TripCourseFragmentArgs by navArgs()

    @Inject
    lateinit var placesClient: PlacesClient

    override fun init() {
        view()
        viewModel()
        observer()
        listener()
    }

    private fun view() {
        val act = FindAutocompletePredictionsRequest.builder()
        act.sessionToken = AutocompleteSessionToken.newInstance()

        val task = placesClient.findAutocompletePredictions(act.build())
        task.addOnSuccessListener { response: FindAutocompletePredictionsResponse? ->
            response?.let {
                Log.d("sudeky","${it.autocompletePredictions}")
            }
        }
        task.addOnFailureListener { exception: Exception ->
            exception.printStackTrace()
            Log.d("sudeky","addOnFailureListener : ${exception.message}")
        }

        task.addOnCompleteListener { response: Task<FindAutocompletePredictionsResponse>? -> Log.d("sudeky","addOnCompleteListener") }

//        (binding.fragmentAutocompleteSupport as AutocompleteSupportFragment).setOnPlaceSelectedListener(object :
//            PlaceSelectionListener{
//            override fun onPlaceSelected(p0: Place) {
//                Log.d("sudeky","onPlaceSelected : ${p0}")
//            }
//
//            override fun onError(p0: Status) {
//                Log.d("sudeky","onError : ${p0}")
//            }
//
//        })
        val search = (childFragmentManager.findFragmentById(R.id.fragment_autocomplete_support) as AutocompleteSupportFragment)
        search.setPlaceFields(listOf(Place.Field.NAME, Place.Field.ADDRESS, Place.Field.ADDRESS_COMPONENTS, Place.Field.LAT_LNG))
        search.setOnPlaceSelectedListener(object: PlaceSelectionListener{
            override fun onPlaceSelected(p0: Place) {
                Log.d("sudeky","onPlaceSelected : ${p0}")
            }

            override fun onError(p0: Status) {
                Log.d("sudeky","onError: ${p0}")
            }

        })
    }

    private fun viewModel() {
        viewModel.init(args.trip)
    }

    private fun observer() {

    }

    private fun listener() {

    }
}