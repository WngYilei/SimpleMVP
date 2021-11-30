package com.zity.sunmi_t1.api;

import EncoderGSonRequestBodyConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xl.base.net.DecoderResponseConverter
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type


class CashConverterFactory(private val gSon: Gson) : Converter.Factory() {
    companion object {
        fun create(gSon: Gson): CashConverterFactory {
            return CashConverterFactory(gSon)
        }
    }

    override fun responseBodyConverter(type: Type?, annotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<ResponseBody, *> {
        val adapter = gSon.getAdapter(TypeToken.get(type))
        return DecoderResponseConverter(adapter)
    }

    override fun requestBodyConverter(type: Type?, parameterAnnotations: Array<out Annotation>?, methodAnnotations: Array<out Annotation>?, retrofit: Retrofit?): Converter<*, RequestBody> {
        val adapter = gSon.getAdapter(TypeToken.get(type))
        return EncoderGSonRequestBodyConverter(gSon, adapter)
    }
}