package com.turtle.amatda.presentation.view.trip_concept

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripConceptBinding
import com.turtle.amatda.domain.model.TripConcept
import com.turtle.amatda.presentation.utilities.EventObserver
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripConceptFragment :
    BaseFragment<TripConceptViewModel, FragmentTripConceptBinding>(R.layout.fragment_trip_concept) {

    private val args: TripConceptFragmentArgs by navArgs()

    override fun init() {
        view()
        viewModel()
        listener()
        observer()
    }

    private fun view() {
        if (args.trip.id != 0L) {
            viewModel.updateConcept(args.trip.type)
        }
    }

    private fun viewModel() {
        binding.viewModel = viewModel
        viewModel.setTrip(args.trip)
    }

    private fun listener() {
        binding.setClickListener {
            viewModel.saveTrip()
            findNavController().navigate(R.id.action_global_view_fragment)
        }
    }

    private fun observer() {
        viewModel.tripConcept.observe(this@TripConceptFragment) { tripConcept ->
            tripConcept?.let { concept ->
                when (concept) {
                    TripConcept.NORMAL -> binding.fragmentTripConceptInclude.radioButtonTripConceptNormal.isChecked =
                        true
                    TripConcept.FUNNY -> binding.fragmentTripConceptInclude.radioButtonTripConceptFunny.isChecked =
                        true
                    TripConcept.ACTIVITY -> binding.fragmentTripConceptInclude.radioButtonTripConceptActivity.isChecked =
                        true
                    TripConcept.REST -> binding.fragmentTripConceptInclude.radioButtonTripConceptRest.isChecked =
                        true
                    TripConcept.LOVELY -> binding.fragmentTripConceptInclude.radioButtonTripConceptLovely.isChecked =
                        true
                    TripConcept.HONEYMOON -> binding.fragmentTripConceptInclude.radioButtonTripConceptHoneymoon.isChecked =
                        true
                    TripConcept.CULTURE -> binding.fragmentTripConceptInclude.radioButtonTripConceptCulture.isChecked =
                        true
                    TripConcept.STUDY -> binding.fragmentTripConceptInclude.radioButtonTripConceptStudy.isChecked =
                        true
                    TripConcept.WORK -> binding.fragmentTripConceptInclude.radioButtonTripConceptWork.isChecked =
                        true
                    TripConcept.FOOD -> binding.fragmentTripConceptInclude.radioButtonTripConceptFood.isChecked =
                        true
                    TripConcept.SELFREFLECTION -> binding.fragmentTripConceptInclude.radioButtonTripConceptSelfreflection.isChecked =
                        true
                    TripConcept.REFRESH -> binding.fragmentTripConceptInclude.radioButtonTripConceptRefresh.isChecked =
                        true
                    TripConcept.BOOK -> binding.fragmentTripConceptInclude.radioButtonTripConceptBook.isChecked =
                        true
                    TripConcept.MUSIC -> binding.fragmentTripConceptInclude.radioButtonTripConceptMusic.isChecked =
                        true
                    TripConcept.CHALLENGER -> binding.fragmentTripConceptInclude.radioButtonTripConceptChallenger.isChecked =
                        true
                    TripConcept.FRIENDSHIP -> binding.fragmentTripConceptInclude.radioButtonTripConceptFriendship.isChecked =
                        true
                }

            }
        }

        viewModel.editMode.observe(this@TripConceptFragment, EventObserver { editMode ->
            if (editMode) {
                binding.btnTripTitleOk.text = "여행 수정"
            }
        })
    }
}