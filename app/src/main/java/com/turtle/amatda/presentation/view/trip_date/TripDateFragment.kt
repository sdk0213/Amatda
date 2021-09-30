package com.turtle.amatda.presentation.view.trip_date

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripDateBinding
import com.turtle.amatda.presentation.utilities.extensions.convertToDateyyyyMMdd
import com.turtle.amatda.presentation.view.base.BaseFragment

class TripDateFragment :
    BaseFragment<TripDateViewModel, FragmentTripDateBinding>(R.layout.fragment_trip_date) {

    private val args: TripDateFragmentArgs by navArgs()

    override fun init() {
        view()
        viewModel()
        listener()
    }

    private fun view() {
    }

    private fun viewModel() {
        viewModel.setTitle(args.trip.title)
    }

    private fun listener() {
        binding.setClickListener {
            viewModel.addTrip()
            findNavController().navigate(R.id.action_global_view_fragment)
        }

        // 오늘 날짜 기준 다음날부터만 선택가능하게 하고 싶을 경우 주석 해제
//        binding.calenderTripDate.state().edit()
//            .setMinimumDate(
//                CalendarDay.from(
//                    getCurrentYear(),
//                    getCurrentMonth(),
//                    getCurrentDay() + 1
//                )
//            )
//            .setCalendarDisplayMode(CalendarMode.MONTHS)
//            .commit()

        binding.calenderTripDate.setOnRangeSelectedListener { _, dates ->
            viewModel.setDate(
                "${dates[0].year}/${dates[0].month}/${dates[0].day}".convertToDateyyyyMMdd(),
                "${dates[dates.size - 1].year}/${dates[dates.size - 1].month}/${dates[dates.size - 1].day}".convertToDateyyyyMMdd(),
            )
            binding.tvTripDateStart.text = "${getString(R.string.question_trip_date_start_day)} : ${dates[0].year}년 ${dates[0].month}월 ${dates[0].day}일"
            binding.tvTripDateEnd.text = "${getString(R.string.question_trip_date_end_day)} : ${dates[dates.size - 1].year}년 ${dates[dates.size - 1].month}월 ${dates[dates.size - 1].day}일"
        }

        binding.calenderTripDate.setOnDateChangedListener { _, _, selected ->
            if(selected){
                binding.tvTripDateStart.text = getString(R.string.question_trip_date_start_day)
                binding.tvTripDateEnd.text = getString(R.string.question_trip_date_end_day)
            }
        }
    }

}