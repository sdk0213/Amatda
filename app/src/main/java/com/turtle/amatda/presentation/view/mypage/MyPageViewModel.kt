package com.turtle.amatda.presentation.view.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.usecases.GetUserUseCase
import com.turtle.amatda.domain.usecases.UpdateUserUseCase
import com.turtle.amatda.presentation.android.shard_pref.SharedPrefUtil
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefUtil: SharedPrefUtil
) : BaseViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val _logout = MutableLiveData<Event<Boolean>>()
    val logout: LiveData<Event<Boolean>> get() = _logout

    private val _updateUser = MutableLiveData<Event<Boolean>>()
    val updateUser: LiveData<Event<Boolean>> get() = _updateUser

    init {
        getUserInformation()
    }

    private fun getUserInformation() {
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

    // 로그아웃
    fun logout() {
        firebaseAuth.signOut()
        sharedPrefUtil.isLoggedDevices = false
        _logout.value = Event(true)
    }

    private fun updateUserInformation() {
        firebaseAuth.currentUser?.let { user ->
            user.reload()
            currentUser.value?.let {
                compositeDisposable.add(
                    updateUserUseCase.execute(
                        User(
                            id = user.uid,
                            email = currentUser.value!!.email,
                            password = currentUser.value!!.password,
                            nickName = currentUser.value!!.nickName,
                            photo = currentUser.value!!.nickName,
                            exp = currentUser.value!!.exp,
                        )
                    )
                        .subscribe(
                            {
                                _updateUser.value = Event(true)
                            },
                            {
                                Timber.e(it)
                            }
                        )
                )
            }
        } ?: run {
            Timber.e("알수없는 유저입니다.")
        }
    }

    fun editNickName(nickName: String) {
        _currentUser.value?.let {
            Timber.d("nickname rename to : $nickName")
            _currentUser.value = User(
                id = _currentUser.value!!.id,
                email = _currentUser.value!!.email,
                password = _currentUser.value!!.password,
                nickName = nickName,
                photo = _currentUser.value!!.photo,
                exp = _currentUser.value!!.exp,
            )
            updateUserInformation()
        }
    }

    fun saveProfileImage(someData: Any) {


    }
}