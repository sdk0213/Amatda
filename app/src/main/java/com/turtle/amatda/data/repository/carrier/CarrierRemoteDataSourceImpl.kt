package com.turtle.amatda.data.repository.carrier

import com.google.firebase.auth.FirebaseAuth
import com.turtle.amatda.data.api.FirebaseFirestoreApiService
import com.turtle.amatda.data.model.CarrierData
import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import io.reactivex.Completable
import javax.inject.Inject

class CarrierRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestoreApiService: FirebaseFirestoreApiService,
    private val firebaseAuth: FirebaseAuth
) : CarrierRemoteDataSource {

    override fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItemsEntity>): Completable {
        return firebaseFirestoreApiService.exportCarrier(
            carrierData = CarrierData(userCarrier)
        )
    }

    override fun importUserCarrierDbServer(): Completable {
        return Completable.create { it.onComplete() }
    }

}