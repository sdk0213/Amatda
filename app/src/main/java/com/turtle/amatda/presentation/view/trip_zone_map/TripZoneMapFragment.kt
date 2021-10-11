//package com.turtle.amatda.presentation.view.trip_zone_map
//
//import android.graphics.Color
//import android.util.Log
//import androidx.navigation.fragment.navArgs
//import com.google.android.gms.common.api.Status
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.MarkerOptions
//import com.google.android.gms.tasks.Task
//import com.google.android.libraries.places.api.model.AutocompleteSessionToken
//import com.google.android.libraries.places.api.model.Place
//import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
//import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
//import com.google.android.libraries.places.api.net.PlacesClient
//import com.google.android.libraries.places.widget.AutocompleteSupportFragment
//import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
//import com.turtle.amatda.R
//import com.turtle.amatda.databinding.FragmentTripCourseBinding
//import com.turtle.amatda.domain.model.MapCameraPosition
//import com.turtle.amatda.presentation.view.base.BaseFragment
//import javax.inject.Inject
//
//class TripZoneMapFragment :
//    BaseFragment<TripZoneMapViewModel, FragmentTripCourseBinding>(R.layout.fragment_trip_course),
//    OnMapReadyCallback {
//
//    @Inject
//    lateinit var placesClient: PlacesClient
//
//    private val args: TripZoneMapFragmentArgs by navArgs()
//
//    private val autocompleteSessionToken: FindAutocompletePredictionsRequest.Builder
//        get() {
//            val act = FindAutocompletePredictionsRequest.builder()
//            act.sessionToken = AutocompleteSessionToken.newInstance()
//            return act
//        }
//
//    private val findAutocompleteTask
//        get() = placesClient.findAutocompletePredictions(autocompleteSessionToken.build())
//
//    private lateinit var mMap: GoogleMap
//
//    private val supportMapFragment
//        get() = (childFragmentManager.findFragmentById(R.id.fragment_google_map) as SupportMapFragment)
//
//    private val searchPlace: AutocompleteSupportFragment
//        get() {
//            val searchPlace =
//                (childFragmentManager.findFragmentById(R.id.fragment_autocomplete_support) as AutocompleteSupportFragment)
//            searchPlace.setPlaceFields(placeFieldList)
//            searchPlace.view?.setBackgroundColor(Color.WHITE)
//            return searchPlace
//        }
//
//    private val placeFieldList = listOf(
//        Place.Field.NAME,
//        Place.Field.ADDRESS,
//        Place.Field.ADDRESS_COMPONENTS,
//        Place.Field.LAT_LNG
//    )
//
//    override fun init() {
//        view()
//        viewModel()
//        observer()
//        listener()
//    }
//
//    private fun view() {
//        supportMapFragment.getMapAsync(this)
//    }
//
//    private fun viewModel() {
//        viewModel.init(args.trip)
//    }
//
//    private fun observer() {
//        viewModel.cameraPosition.observe(this@TripZoneMapFragment) {
//            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(it.latLng, it.zoom))
//        }
//
//        viewModel.newPlace.observe(this@TripZoneMapFragment) {
//            mMap.addMarker(MarkerOptions().position(it.latLng!!).title(it.name))
//        }
//    }
//
//    private fun listener() {
//        findAutocompleteTask.addOnSuccessListener { response: FindAutocompletePredictionsResponse? ->
//            response?.let {
//                Log.d(TAG, "${it.autocompletePredictions}")
//            }
//        }
//        findAutocompleteTask.addOnFailureListener { exception: Exception ->
//            exception.printStackTrace()
//            Log.d(TAG, "addOnFailureListener : ${exception.message}")
//        }
//
//        findAutocompleteTask.addOnCompleteListener { response: Task<FindAutocompletePredictionsResponse>? ->
//            Log.d(
//                TAG,
//                "addOnCompleteListener : ${response}"
//            )
//        }
//
//        searchPlace.setOnPlaceSelectedListener(object : PlaceSelectionListener {
//            override fun onPlaceSelected(place: Place) {
//                Log.d(TAG, "onPlaceSelected : ${place}")
//                viewModel.addMarkerToMap(place)
//                viewModel.moveCamera(MapCameraPosition(place.latLng!!, 17.5f))
//            }
//
//            override fun onError(status: Status) {
//                Log.d(TAG, "onError_status : ${status}")
//            }
//
//        })
//    }
//
//    override fun onMapReady(googleMap: GoogleMap?) {
//        googleMap?.let {
//            mMap = it
//        } ?: run {
//            Log.d(TAG, "googleMap is Null")
//        }
//    }
//}