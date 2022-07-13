package com.example.cointracker.data.util.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.cointracker.data.model.Quote
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@ProvidedTypeConverter
class Converters(
    private val jsonParser: JsonParser
) {

    @TypeConverter
    fun toQuoteJson(quote: Quote): String{
        return jsonParser.toJson(
            quote,
            object : TypeToken<Quote>(){}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromQuoteJson(json: String): Quote{
        return jsonParser.fromJson<Quote>(
            json,
            object: TypeToken<Quote>(){}.type
        ) ?: Quote(null)
    }

    @TypeConverter
    fun toStringJson(meaning: List<String?>?) : String {
        return jsonParser.toJson(
            meaning,
            object : TypeToken<List<String>>(){}.type
        ) ?: "[]"
    }
    @TypeConverter
    fun fromStringsJson(json: String): List<String>{
        return jsonParser.fromJson<List<String>>(
            json,
            object: TypeToken<List<String>>(){}.type
        ) ?: emptyList()
    }
}