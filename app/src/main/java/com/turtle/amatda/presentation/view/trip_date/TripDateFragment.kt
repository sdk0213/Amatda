package com.turtle.amatda.presentation.view.trip_date

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripDateBinding
import com.turtle.amatda.presentation.utilities.extensions.convertToDateyyyyMMdd
import com.turtle.amatda.presentation.utilities.extensions.toCalenderDay
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class TripDateFragment :
    BaseFragment<TripDateViewModel, FragmentTripDateBinding>(R.layout.fragment_trip_date) {

    private val args: TripDateFragmentArgs by navArgs()

    override fun init() {
        view()
        viewModel()
        listener()
    }

    private fun view() {
        // 아이템 수정모드일경우 (ID 값이 0 이 아님)
        if (args.trip.id != 0L) {
            val startCal = args.trip.date_start.toCalenderDay()
            val endCal = args.trip.date_end.toCalenderDay()
            binding.calenderTripDate.selectRange(startCal, endCal)

            binding.tvTripDateStart.text =
                "${getString(R.string.question_trip_date_start_day)} : ${startCal.year}년 ${
                    startCal.month
                }월 ${startCal.day}일"
            binding.tvTripDateEnd.text =
                "${getString(R.string.question_trip_date_end_day)} : ${endCal.year}년 ${
                    endCal.month
                }월 ${endCal.day}일"

            viewModel.setDate(args.trip.date_start, args.trip.date_end)
        }
    }

    private fun viewModel() {
        viewModel.setTrip(args.trip)
    }

    private fun listener() {
        binding.setClickListener {
            findNavController().navigate(
                TripDateFragmentDirections.actionTripDateFragmentToTripConceptFragment(
                    viewModel.getTrip()
                )
            )
        }

        binding.calenderTripDate.setOnRangeSelectedListener { _, dates ->
            viewModel.setDate(
                "${dates[0].year}/${dates[0].month}/${dates[0].day}".convertToDateyyyyMMdd(),
                "${dates[dates.size - 1].year}/${dates[dates.size - 1].month}/${dates[dates.size - 1].day}".convertToDateyyyyMMdd(),
            )
            binding.tvTripDateStart.text =
                "${getString(R.string.question_trip_date_start_day)} : ${dates[0].year}년 ${dates[0].month}월 ${dates[0].day}일"
            binding.tvTripDateEnd.text =
                "${getString(R.string.question_trip_date_end_day)} : ${dates[dates.size - 1].year}년 ${dates[dates.size - 1].month}월 ${dates[dates.size - 1].day}일"
        }

        binding.calenderTripDate.setOnDateChangedListener { _, _, selected ->
            if (selected) {
                binding.tvTripDateStart.text = getString(R.string.question_trip_date_start_day)
                binding.tvTripDateEnd.text = getString(R.string.question_trip_date_end_day)
            }
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
    }

}