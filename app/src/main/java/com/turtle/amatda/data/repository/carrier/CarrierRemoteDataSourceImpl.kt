package com.turtle.amatda.data.repository.carrier

import com.turtle.amatda.data.api.FirebaseFirestoreApiService
import com.turtle.amatda.data.model.CarrierData
import com.turtle.amatda.data.model.CarrierWithPocketAndItemsEntity
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class CarrierRemoteDataSourceImpl @Inject constructor(
    private val firebaseFirestoreApiService: FirebaseFirestoreApiService
) : CarrierRemoteDataSource {

    override fun exportUserCarrierDbServer(userCarrier: List<CarrierWithPocketAndItemsEntity>): Completable {
        return firebaseFirestoreApiService.exportCarrier(
            carrierData = CarrierData(carrierData = userCarrier)
        )
    }

    override fun importUserCarrierDbServer(): Single<List<CarrierWithPocketAndItemsEntity>> {
        return firebaseFirestoreApiService.importCarrier()
    }

}