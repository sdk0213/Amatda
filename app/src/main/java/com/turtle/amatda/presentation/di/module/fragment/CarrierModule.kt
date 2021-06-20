package com.turtle.amatda.presentation.di.module.fragment

import androidx.databinding.DataBindingUtil
import com.turtle.amatda.R
import com.turtle.amatda.databinding.FragmentCarrierBinding
import com.turtle.amatda.presentation.di.scope.FragmentScope
import com.turtle.amatda.presentation.view.main.MainActivity
import dagger.Module
import dagger.Provides

@Module
class CarrierModule {

    @Provides
    @FragmentScope
    fun provideFragmentCarrierBinding(activity: MainActivity): FragmentCarrierBinding {
        return DataBindingUtil.inflate<FragmentCarrierBinding>(
            activity.layoutInflater,
            R.layout.fragment_carrier,
            null,
            false
        )
    }

    //    //RecyclerView용 레이아웃 매니저
//    @Provides
//    @FragmentScope
//    fun provideLinearLayoutManager(@ApplicationContext context: Context): LinearLayoutManager {
//        return object : LinearLayoutManager(context) {
//            override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
//                return RecyclerView.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT
//                )
//            }
//        }
//    }
//
//    //Navigation 컴포넌트에서 목적지 간 이동을 담당하는 NavController
//    @Provides
//    @FragmentScope
//    fun provideNavController(fragment: TripFragment): NavController {
//        return NavHostFragment.findNavController(fragment)
//    }


}