package com.turtle.amatda.data.model

import androidx.room.*
import com.turtle.amatda.domain.model.ZoneType
import java.util.*

@Entity(
    tableName = "TripZone", foreignKeys = [
        ForeignKey(
            entity = TripEntity::class,
            parentColumns = ["trip_id"],
            childColumns = ["trip_id_foreign"],
            onDelete = ForeignKey.CASCADE
        )]
)
data class TripZoneEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "trip_zone_id")
    val id: Long,

    @ColumnInfo(name = "trip_zone_area")
    val area: String = "알수 없음", // 지역명

    @ColumnInfo(name = "trip_zone_title")
    val title: String = "알수 없음", // 제목

    @ColumnInfo(name = "trip_zone_date")
    val date: Date = Date(), // 방문 날짜

    @ColumnInfo(name = "trip_zone_lat")
    val lat: String = "0", // 위도

    @ColumnInfo(name = "trip_zone_lon")
    val lon: String = "0", // 경도

    @ColumnInfo(name = "trip_zone_type")
    val zoneType: ZoneType = ZoneType.NONE, // 존 타입

    @ColumnInfo(name = "trip_id_foreign", index = true)
    val trip_id: Long,
)

data class TripAndTripZoneEntity(
    @Embedded
    val tripEntity: TripEntity,

    @Relation(
        parentColumn = "trip_id",
        entityColumn = "trip_id_foreign"
    )
    val tripZoneEntityList: List<TripZoneEntity>
)