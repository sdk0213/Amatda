package com.turtle.amatda.presentation.view.mypage

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.turtle.amatda.domain.model.ErrorMessage
import com.turtle.amatda.domain.model.UploadFile
import com.turtle.amatda.domain.model.User
import com.turtle.amatda.domain.usecases.*
import com.turtle.amatda.presentation.android.shard_pref.SharedPrefUtil
import com.turtle.amatda.presentation.utilities.Event
import com.turtle.amatda.presentation.view.base.BaseViewModel
import timber.log.Timber
import javax.inject.Inject

class MyPageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase,
    private val updateUserFileUseCase: UpdateUserFileUseCase,
    private val getAllCarrierDbUseCase: GetAllCarrierDbUseCase,
    private val exportDbServerUseCase: ExportDbServerUseCase,
    private val importDbServerUseCase: ImportDbServerUseCase,
    private val deleteCarrierDBUseCase: DeleteCarrierDBUseCase,
    private val insertCarrierDBUseCase: InsertCarrierDBUseCase,
    private val firebaseAuth: FirebaseAuth,
    private val sharedPrefUtil: SharedPrefUtil
) : BaseViewModel() {

    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val _logout = MutableLiveData<Event<Boolean>>()
    val logout: LiveData<Event<Boolean>> get() = _logout

    private val _updateUser = MutableLiveData<Event<Boolean>>()
    val updateUser: LiveData<Event<Boolean>> get() = _updateUser

    private val _updateDB = MutableLiveData<Event<Boolean>>()
    val updateDB: LiveData<Event<Boolean>> get() = _updateDB

    private val _restoreDB = MutableLiveData<Event<Boolean>>()
    val restoreDB: LiveData<Event<Boolean>> get() = _restoreDB

    private val _noDataInServerDB = MutableLiveData<Event<Boolean>>()
    val noDataInServerDB: LiveData<Event<Boolean>> get() = _noDataInServerDB

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
        compositeDisposable.add(
            deleteCarrierDBUseCase.execute()
                .subscribe(
                    {
                        firebaseAuth.signOut()
                        sharedPrefUtil.isLoggedDevices = false
                        _logout.value = Event(true)
                    },
                    {
                        _logout.value = Event(false)
                    }
                )
        )
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
                            photo = currentUser.value!!.photo,
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

    fun uploadProfileImage(uri: Uri) {
        _currentUser.value?.let {
            compositeDisposable.add(
                updateUserFileUseCase.execute(
                    UploadFile(
                        fileName = _currentUser.value!!.id,
                        fileUri = uri
                    )
                )
                    .subscribe(
                        { fileDownloadUri ->
                            Timber.d("image upload complete")
                            _currentUser.value = User(
                                id = _currentUser.value!!.id,
                                email = _currentUser.value!!.email,
                                password = _currentUser.value!!.password,
                                nickName = _currentUser.value!!.nickName,
                                photo = fileDownloadUri,
                                exp = _currentUser.value!!.exp,
                            )
                            updateUserInformation()
                        },
                        {
                            Timber.e("upload user profile image is onError: $it")
                        }
                    )
            )
        }
    }

    fun exportCarrierDataToDbUseCase() {
        compositeDisposable.add(
            getAllCarrierDbUseCase.execute()
                .flatMapCompletable { carrierList ->
                    exportDbServerUseCase.execute(carrierList)
                }
                .subscribe(
                    {
                        _updateDB.value = Event(true)
                    },
                    {
                        Timber.e(it)
                    }
                )

        )
    }

    fun importCarrierDataToDbUseCase() {
        compositeDisposable.add(
            importDbServerUseCase.execute()
                .subscribe(
                    { listCarrier ->
                        compositeDisposable.add(
                            deleteCarrierDBUseCase.execute()
                                .subscribe(
                                    {
                                        compositeDisposable.add(
                                            insertCarrierDBUseCase.execute(listCarrier)
                                                .subscribe(
                                                    {
                                                        _restoreDB.value = Event(true)
                                                    },
                                                    { throwable ->
                                                        Timber.e(throwable)
                                                    }
                                                )
                                        )
                                    },
                                    { throwable ->
                                        Timber.e(throwable)
                                    }
                                )
                        )
                    },
                    { throwable ->
                        when (throwable.message) {
                            ErrorMessage.NO_DATA.message -> _noDataInServerDB.value = Event(true)
                            ErrorMessage.NO_USER.message -> Timber.e("알수없는 회원입니다.")
                            else -> Timber.e(throwable)
                        }
                    }
                )

        )
    }
}