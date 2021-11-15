package com.turtle.amatda.data.model

import androidx.room.*
import java.util.*

@Entity(tableName = "Carrier")
data class CarrierEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "carrier_id")
    val id: Long,
    @ColumnInfo(name = "carrier_name")
    val name: String,
    @ColumnInfo(name = "carrier_date")
    val date: Date,
    @ColumnInfo(name = "carrier_type")
    val type: String,
    @ColumnInfo(name = "carrier_size")
    val size: String,
)

// 캐리어 포함 하위 관련 모든 DB 가져오기
data class CarrierWithPocketAndItemsEntity(
    @Embedded
    val carrier: CarrierEntity,

    @Relation(
        entity = PocketEntity::class,
        parentColumn = "carrier_id",
        entityColumn = "carrier_id_foreign"
    )
    val pockets: List<PocketAndItemsEntity>
)

data class CarrierData(
    val carrierData: List<CarrierWithPocketAndItemsEntity>
)
