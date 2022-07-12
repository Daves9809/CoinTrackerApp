package com.example.cointracker.data.util.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.cointracker.data.model.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters(
) {
    private val gson = Gson()

    @TypeConverter
    fun toQuoteJson(quote: Quote): String{
        return gson.toJson(
            quote,
            object : TypeToken<Quote>(){}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromQuoteJson(json: String): Quote{
        return gson.fromJson<Quote>(
            json,
            object: TypeToken<Quote>(){}.type
        ) ?: Quote(null)
    }

    @TypeConverter
    fun toStringJson(meaning: List<String?>?) : String {
        return gson.toJson(
            meaning,
            object : TypeToken<List<String>>(){}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromStringsJson(json: String): List<String>{
        return gson.fromJson<List<String>>(
            json,
            object: TypeToken<List<String>>(){}.type
        ) ?: emptyList()
    }
}