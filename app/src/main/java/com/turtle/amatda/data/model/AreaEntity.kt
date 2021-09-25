package com.turtle.amatda.data.model

import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

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