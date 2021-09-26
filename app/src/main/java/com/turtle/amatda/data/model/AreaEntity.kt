package com.turtle.amatda.data.model

import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Xml(name="response")
data class AreaXml(
    @Element
    val header: AreaXmlHeader,
    @Element
    val body: AreaXmlBody
)

@Xml(name="header")
data class AreaXmlHeader(
    @PropertyElement
    val resultCode: Int?,
    @PropertyElement
    val resultMsg: String?
)

@Xml(name="body")
data class AreaXmlBody(
    @Element
    val items: AreaXmlItems,
    @PropertyElement
    val numOfRows: String?,
    @PropertyElement
    val pageNo: String?,
    @PropertyElement
    val totalCount: String?
)

@Xml(name="items")
data class AreaXmlItems(
    @Element(name = "item")
    val item: List<AreaEntity>?
)

@Xml(name="item")
data class AreaEntity (
    @PropertyElement(name="code")
    val code: String?,
    @PropertyElement(name="name")
    val name: String?,
    @PropertyElement(name="rnum")
    val rnum : String?
){
    constructor() : this(null,null,null)
}
