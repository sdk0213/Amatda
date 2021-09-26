package com.turtle.amatda.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class TourXml(
    @Element
    val header: TourXmlHeader,
    @Element
    val body: TourXmlBody
)

@Xml(name="header")
data class TourXmlHeader(
    @PropertyElement
    val resultCode: Int?,
    @PropertyElement
    val resultMsg: String?
)

@Xml(name="body")
data class TourXmlBody(
    @Element
    val items: TourXmlItems,
    @PropertyElement
    val numOfRows: String?,
    @PropertyElement
    val pageNo: String?,
    @PropertyElement
    val totalCount: String?
)

@Xml(name="items")
data class TourXmlItems(
    @Element(name = "item")
    val item: List<TourEntity>?
)

@Xml(name="item")
data class TourEntity (
    @PropertyElement(name="addr1")
    val addr1: String?,
    @PropertyElement(name="areacode")
    val areacode: String?,
    @PropertyElement(name="cat1")
    val cat1 : String?,
    @PropertyElement(name="cat2")
    val cat2 : String?,
    @PropertyElement(name="cat3")
    val cat3 : String?,
    @PropertyElement(name="contentid")
    val contentid : String?,
    @PropertyElement(name="createdtime")
    val createdtime : String?,
    @PropertyElement(name="mapx")
    val mapx : String?,
    @PropertyElement(name="mapy")
    val mapy : String?,
    @PropertyElement(name="mlevel")
    val mlevel : String?,
    @PropertyElement(name="modifiedtime")
    val modifiedtime : String?,
    @PropertyElement(name="readcount")
    val readcount : String?,
    @PropertyElement(name="sigungucode")
    val sigungucode : String?,
    @PropertyElement(name="title")
    val title : String?,
    @PropertyElement(name="zipcode")
    val zipcode : String?
)
