package com.turtle.amatda.data.mapper

interface Mapper<E, D> {

    fun entityToMap(type: E): D

    fun mapToEntity(type: D): E

}