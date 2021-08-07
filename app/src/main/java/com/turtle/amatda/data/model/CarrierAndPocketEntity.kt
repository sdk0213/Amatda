package com.turtle.amatda.data.model

import androidx.room.*
import java.util.*

@Entity(
    tableName = "Pocket", foreignKeys = [
        ForeignKey(
            entity = CarrierEntity::class,
            parentColumns = ["carrier_id"],
            childColumns = ["carrier_id_foreign"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class PocketEntity (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pocket_id")
    val id: Date,

    @ColumnInfo(name = "pocket_name")
    val name: String,

    @ColumnInfo(name = "carrier_id_foreign", index = true)
    val carrier_id: Long,
)

data class CarrierAndPocketEntity(
    @Embedded
    val carrier: CarrierEntity,

    @Relation(
        parentColumn = "carrier_id",
        entityColumn = "carrier_id_foreign"
    )
    val pockets: List<PocketEntity>
)