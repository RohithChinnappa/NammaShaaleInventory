package com.nammashaale.inventory.data.db

import androidx.room.TypeConverter
import com.nammashaale.inventory.data.model.Condition

class Converters {
    @TypeConverter
    fun fromCondition(c: Condition): String = c.name

    @TypeConverter
    fun toCondition(s: String): Condition = Condition.valueOf(s)
}