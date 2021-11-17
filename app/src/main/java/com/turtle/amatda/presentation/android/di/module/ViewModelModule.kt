package com.turtle.amatda.presentation.android.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.turtle.amatda.presentation.android.di.factory.ViewModelFactory
import com.turtle.amatda.presentation.android.di.key.ViewModelKey
import com.turtle.amatda.presentation.view.carrier.CarrierViewModel
import com.turtle.amatda.presentation.view.carrier_item.CarrierItemViewModel
import com.turtle.amatda.presentation.view.carrier_name.CarrierNameViewModel
import com.turtle.amatda.presentation.view.carrier_size.CarrierSizeViewModel
import com.turtle.amatda.presentation.view.carrier_type.CarrierTypeViewModel
import com.turtle.amatda.presentation.view.home.HomeViewModel
import com.turtle.amatda.presentation.view.intro.IntroViewModel
import com.turtle.amatda.presentation.view.login_sign_in.LoginSignInViewModel
import com.turtle.amatda.presentation.view.login_sign_in_with_email.LoginSignInWithEmailViewModel
import com.turtle.amatda.presentation.view.login_sign_up.LoginSignUpViewModel
import com.turtle.amatda.presentation.view.main.MainViewPagerModel
import com.turtle.amatda.presentation.view.mypage.MyPageViewModel
import com.turtle.amatda.presentation.view.qna.QNAViewModel
import com.turtle.amatda.presentation.view.trip.TripViewModel
import com.turtle.amatda.presentation.view.trip_concept.TripConceptViewModel
import com.turtle.amatda.presentation.view.trip_date.TripDateViewModel
import com.turtle.amatda.presentation.view.trip_zone.TripZoneViewModel
import com.turtle.amatda.presentation.view.trip_zone_make.TripZoneMakeViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(IntroViewModel::class)
    abstract fun bindsIntroViewModel(viewModel: IntroViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginSignInViewModel::class)
    abstract fun bindsLoginSignInViewModel(signInViewModel: LoginSignInViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginSignInWithEmailViewModel::class)
    abstract fun bindsLoginSignInWithEmailViewModel(viewModelSignIn: LoginSignInWithEmailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginSignUpViewModel::class)
    abstract fun bindsLoginSignUpViewModel(viewModelSignIn: LoginSignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewPagerModel::class)
    abstract fun bindsMainViewPagerViewModel(viewModel: MainViewPagerModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindsHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarrierViewModel::class)
    abstract fun bindsCarrierViewModel(viewModel: CarrierViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarrierTypeViewModel::class)
    abstract fun bindsCarrierTypeViewModel(viewModel: CarrierTypeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarrierSizeViewModel::class)
    abstract fun bindsCarrierSizeViewModel(viewModel: CarrierSizeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarrierNameViewModel::class)
    abstract fun bindsCarrierNameViewModel(viewModel: CarrierNameViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(CarrierItemViewModel::class)
    abstract fun bindsCarrierItemViewModel(viewModel: CarrierItemViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripViewModel::class)
    abstract fun bindsTripViewModel(viewModel: TripViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MyPageViewModel::class)
    abstract fun bindsMyPageViewModel(viewModel: MyPageViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripDateViewModel::class)
    abstract fun bindsTripDateViewModel(viewModel: TripDateViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripConceptViewModel::class)
    abstract fun bindsTripConceptViewModel(viewModel: TripConceptViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripZoneViewModel::class)
    abstract fun bindsTripZoneViewModel(viewModel: TripZoneViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(TripZoneMakeViewModel::class)
    abstract fun bindsTripZoneMakeViewModel(viewModel: TripZoneMakeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QNAViewModel::class)
    abstract fun bindsQNAViewModel(viewModel: QNAViewModel): ViewModel
}