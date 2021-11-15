package com.turtle.amatda.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "Carrier")
data class CarrierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "carrier_id")
    val id: Long = 0,
    @ColumnInfo(name = "carrier_name")
    val name: String = "null",
    @ColumnInfo(name = "carrier_date")
    val date: Date = Date(),
    @ColumnInfo(name = "carrier_type")
    val type: String = "null",
    @ColumnInfo(name = "carrier_size")
    val size: String = "null",
)

// 캐리어 포함 하위 관련 모든 DB 가져오기
data class CarrierWithPocketAndItemsEntity(
    @Embedded
    val carrier: CarrierEntity = CarrierEntity(),

    @Relation(
        entity = PocketEntity::class,
        parentColumn = "carrier_id",
        entityColumn = "carrier_id_foreign"
    )
    val pockets: List<PocketAndItemsEntity> = arrayListOf()
)

data class CarrierData(
    val time_stamp: Date = Date(),
    val carrierData: List<CarrierWithPocketAndItemsEntity> = arrayListOf()
)
