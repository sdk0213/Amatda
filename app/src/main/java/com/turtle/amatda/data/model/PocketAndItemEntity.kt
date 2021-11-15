package com.turtle.amatda.data.model

import androidx.room.*
import java.util.*

@Entity(
    tableName = "Item", foreignKeys = [
        ForeignKey(
            entity = PocketEntity::class,
            parentColumns = ["pocket_id"],
            childColumns = ["pocket_id_foreign"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class ItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "item_id")
    val id: Date = Date(),

    @ColumnInfo(name = "item_name")
    val name: String = "null",

    @ColumnInfo(name = "item_count")
    val count: Int = 1,

    @ColumnInfo(name = "item_color")
    val color: Long = 0xFFFFFFFF,

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
    val checked: Boolean = false,

    @ColumnInfo(name = "item_place")
    val item_place: Int = 0,

    @ColumnInfo(name = "pocket_id_foreign", index = true)
    val pocket_id: Date = Date(),
)

data class PocketAndItemsEntity(
    @Embedded
    val pocket: PocketEntity = PocketEntity(),

    @Relation(
        parentColumn = "pocket_id",
        entityColumn = "pocket_id_foreign"
    )
    val items: List<ItemEntity> = arrayListOf()
)