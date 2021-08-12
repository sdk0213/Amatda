package com.turtle.amatda.presentation.view.carrier

import android.util.Log
import android.view.WindowManager
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import br.com.mauker.materialsearchview.MaterialSearchView
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierBinding
import com.turtle.amatda.presentation.view.base.BaseFragment
import java.util.*

class CarrierFragment : BaseFragment<CarrierViewModel, FragmentCarrierBinding>(R.layout.fragment_carrier) {

    private lateinit var carrierAdapter: CarrierAdapter

    private val arrayOfAllItem = arrayListOf<String>()
    private val mapOfPocketIdAndPocketName = mutableMapOf<Date,String>()
    private val mapOfPocketIdAndCarrierName = mutableMapOf<Date,String>()

    override fun init() {

        initAdapter()

        viewModel.getCarrierList()
        viewModel.getAllItem()

        observer()
        listener()
        onBackPressed()
    }

    private fun initAdapter() {
        carrierAdapter = CarrierAdapter(
            clickCarrier = {
                findNavController().navigate(CarrierFragmentDirections.actionGlobalCarrierItemFragment(it))
            }
        )
        binding.carrierRecyclerView.adapter = carrierAdapter
    }

    private fun observer(){

        viewModel.mCarrierAndGetHasPocketNum.observe(this){
            carrierAdapter.submitList(it)
        }

        viewModel.allCarrierPocketList.observe(viewLifecycleOwner) { allCarrierAndPokcet ->
            mapOfPocketIdAndCarrierName.clear()
            mapOfPocketIdAndPocketName.clear()
            allCarrierAndPokcet.forEach{ carrierAndPocket ->
                carrierAndPocket.pockets.forEach { pocket ->
                    mapOfPocketIdAndCarrierName[pocket.id] = carrierAndPocket.carrier.name
                    mapOfPocketIdAndPocketName[pocket.id] = pocket.name
                }
            }
        }

        viewModel.allItemList.observe(viewLifecycleOwner) { listOfAllItem ->
            arrayOfAllItem.clear()
            listOfAllItem.forEach{ item ->
                // "새 물품 (캐리어가방 - 큰주머니 - 바깥쪽)",
                arrayOfAllItem.add("${item.name} (${mapOfPocketIdAndCarrierName[item.pocket_id]} - ${mapOfPocketIdAndPocketName[item.pocket_id]} - ${
                    when(item.item_place){
                        0 -> "안쪽"
                        1 -> "가운데 쪽"
                        else ->"바깥 쪽"
                    }
                })")
            }
        }

    }

    private fun listener(){

        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_add_carrier -> {
                    findNavController().navigate(CarrierFragmentDirections.actionGlobalCarrierTypeFragment())
                    true
                }
                R.id.item_search -> {
                    binding.searchView.openSearch()
                    true
                }
                else -> {
                    true
                }
            }
        }

        binding.searchView.apply {
            adjustTintAlpha(0.85f)
            setShouldKeepHistory(false)
            setShouldAnimate(true)


            setOnQueryTextListener(object : MaterialSearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    if(!arrayOfAllItem.any { it.contains(query) }){
                        showToast("검색된 물품이 없습니다.")
                    }
                    return true
                }

                override fun onQueryTextChange(newText: String): Boolean {
                    Log.d("sudeky","onQueryTextChange is worked")
                    return false
                }
            })

            setSearchViewListener(object : MaterialSearchView.SearchViewListener {
                override fun onSearchViewOpened() {
                    activity?.window?.statusBarColor =
                        ContextCompat.getColor(mContext, R.color.white_ish)

                    binding.searchView.clearSuggestions()
                    binding.searchView.addSuggestions(arrayOfAllItem)
                }

                override fun onSearchViewClosed() {
                    activity?.window?.statusBarColor =
                        ContextCompat.getColor(mContext, R.color.amatda_main)
                }
            })

            setOnItemClickListener { _, _, position, _ ->
                // 아무것도 하지 않는다.
                // todo : 아이템 클릭시 해당 아이템으로 이동은 추후에 개발
            }
        }

    }

    private fun onBackPressed() =
        requireActivity().onBackPressedDispatcher.addCallback(
            binding.lifecycleOwner!!,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.searchView.isOpen) {
                        binding.searchView.closeSearch()
                    } else {
                        requireActivity().finish()
                    }
                }
            })
}