package com.turtle.amatda.presentation.view.trip_date

import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.DayViewDecorator
import com.prolificinteractive.materialcalendarview.DayViewFacade
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentTripDateBinding
import com.turtle.amatda.presentation.utilities.EventObserver
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
        observer()
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
        viewModel.getAllTrip()
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

    private fun observer() {
        viewModel.disabledDate.observe(this@TripDateFragment) { hashSetOfDisabledCalendarDay ->
            binding.calenderTripDate.addDecorator(
                object: DayViewDecorator{
                    override fun shouldDecorate(day: CalendarDay?): Boolean {
                        return hashSetOfDisabledCalendarDay.contains(day)
                    }

                    override fun decorate(view: DayViewFacade?) {
                        view?.setDaysDisabled(true)
                        ContextCompat.getDrawable(mContext, R.drawable.ic_baseline_cancel_24)?.let {
                            view?.setBackgroundDrawable(it)
                        }
                    }

                }
            )
        }

        viewModel.tripStartDate.observe(this@TripDateFragment) {
            val calendar = it.toCalenderDay()
            binding.tvTripDateStart.text =
                "${getString(R.string.question_trip_date_start_day)} : ${calendar.year}년 ${calendar.month}월 ${calendar.day}일"
        }

        viewModel.tripEndDate.observe(this@TripDateFragment) {
            val calendar = it.toCalenderDay()
            binding.tvTripDateEnd.text =
                "${getString(R.string.question_trip_date_end_day)} : ${calendar.year}년 ${calendar.month}월 ${calendar.day}일"
        }

        viewModel.isWrongDate.observe(this@TripDateFragment, EventObserver { needToReselect ->
            if(needToReselect){
                binding.calenderTripDate.clearSelection()
                showToast("선택하신 날짜에 다른 여행이 계획되어있습니다.")
            }
        })
    }

}