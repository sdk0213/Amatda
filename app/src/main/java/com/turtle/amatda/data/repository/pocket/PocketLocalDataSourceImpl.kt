package com.turtle.amatda.data.repository.pocket

import com.turtle.amatda.data.db.dao.PocketDao
import com.turtle.amatda.data.model.PocketAndItemsEntity
import com.turtle.amatda.data.model.PocketEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

class PocketLocalDataSourceImpl @Inject constructor(private val pocketDao: PocketDao) : PocketLocalDataSource{

    override fun getPocketAll(): Flowable<List<PocketEntity>> {
        return pocketDao.getAll()
    }

    override fun insertPocket(pocket: PocketEntity): Completable {
        return pocketDao.insert(pocket)
    }

    override fun getPocketAndItems(): Flowable<List<PocketAndItemsEntity>> {
        return pocketDao.getPocketAndItemData()
    }


}