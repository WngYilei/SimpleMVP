package com.xl.base.net

import android.util.Log
import com.google.gson.TypeAdapter
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Converter

class DecoderResponseConverter<T>(private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {
    override fun convert(value: ResponseBody): T {
        return try {
            val resp = value.string()
            val json = JSONObject(resp)
            val code = json.optInt("code")
            if (code == 0) {
                val data = json.optString("data")

                if ("null" == data || data == null) {
                    val json1 = JSONObject()
                    json1.put("data", "")
                    adapter.fromJson(json1.toString())
                } else {
                    adapter.fromJson(data)
                }
            } else {
//                val errMsg = json.optString("message")
                val info = json.optString("info")
                if (info.isNotEmpty()) {
                    throw RuntimeException(ApiError(code, info, null))
                } else {
                    throw RuntimeException(ApiError(code, "未知错误", null))
                }
            }
        } catch (e: Exception) {
            val cause = e.cause
            if (cause is ApiError) {
                throw RuntimeException(cause)
            } else {
                throw RuntimeException(ApiError(-1, "unknown", e))
            }
        } finally {
            value.close()
        }
    }
}