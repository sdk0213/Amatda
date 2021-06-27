package com.turtle.amatda.presentation.di.module

import com.turtle.amatda.data.mapper.CarrierMapper
import com.turtle.amatda.data.mapper.ItemMapper
import com.turtle.amatda.data.mapper.Mapper
import com.turtle.amatda.data.model.CarrierEntity
import com.turtle.amatda.data.model.ItemEntity
import com.turtle.amatda.domain.model.Carrier
import com.turtle.amatda.domain.model.Item
import dagger.Module
import dagger.Provides

@Module
class MapperModule {

    @Provides
    fun provideItemMapper(): Mapper<ItemEntity, Item>{
        return ItemMapper()
    }

    @Provides
    fun provideTodoMapper(): Mapper<CarrierEntity, Carrier>{
        return CarrierMapper()
    }
}