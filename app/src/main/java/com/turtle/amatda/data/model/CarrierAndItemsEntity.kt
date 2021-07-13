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

@Entity(tableName = "Item", foreignKeys = [
    ForeignKey(
        entity = CarrierEntity::class,
        parentColumns = ["carrier_id"],
        childColumns = ["carrier_id_foreign"],
        onDelete = ForeignKey.CASCADE
    )])
data class ItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val id: Date,

    @ColumnInfo(name = "item_name")
    val name: String,

    @ColumnInfo(name = "item_count")
    val count: Int = 1,

    @ColumnInfo(name = "item_position_x")
    val position_x: Float = 0f,

    @ColumnInfo(name = "item_position_y")
    val position_y: Float = 0f,

    @ColumnInfo(name = "item_priority")
    val priority: Long = 0,

    @ColumnInfo(name = "item_width")
    val item_width: Int = 250,
    @ColumnInfo(name = "item_height")
    val item_height: Int = 250,

    @ColumnInfo(name = "item_checked")
    val checked : Boolean = false,

    @ColumnInfo(name = "carrier_id_foreign", index = true)
    val carrier_id: Long
)

data class CarrierAndItemsEntity(
    @Embedded
    val carrier: CarrierEntity,

    @Relation(
        parentColumn = "carrier_id",
        entityColumn = "carrier_id_foreign"
    )
    val items: List<ItemEntity>
)
