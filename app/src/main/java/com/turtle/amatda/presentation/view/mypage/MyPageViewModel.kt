package com.turtle.amatda.presentation.view.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.turtle.amatda.domain.model.Trip
import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.usecases.GetUserUseCase
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    init {
        getUserInformation()
    }

    private fun getUserInformation(){
        firebaseAuth.currentUser?.let { user ->
            user.reload()
            compositeDisposable.add(
                getUserUseCase.execute(
                    User(
                        id = user.uid
                    )
                )
                    .subscribe(
                        { user ->
                            _currentUser.value = user
                        },
                        { throwable ->
                            Timber.e("getUserUseCase is Error: $throwable")
                        }
                    )

            )
        } ?: run {
            Timber.e("알수없는 유저입니다.")
        }
    }

    fun saveProfileImage(someData: Any){


    }
}