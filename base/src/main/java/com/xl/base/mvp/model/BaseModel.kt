package com.xl.base.mvp.model

import com.xl.base.net.ApiManager
import com.xl.base.mvp.contract.IModelContract
import com.zity.sunmi_t1.mvp.model.IBaseModel
import okhttp3.RequestBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class BaseModel : IBaseModel, IModelContract {

//    val apiService = ApiManager.provideApiService()

    fun getJsonStr(vararg params: Pair<String, Any>): String {
        return getJson(*params).toString()
    }



    fun getJson(vararg params: Pair<String, Any>): JSONObject {
        val json = JSONObject()
        params.forEach { (key, value) ->
            json.put(key, value)
        }
        return json
    }

    fun getJsonBody(vararg params: Pair<String, Any>): RequestBody {
        return ApiManager.genBody(getJson(*params))
    }

    fun getJsonMap(vararg params: Pair<String, Any>): HashMap<String, Any> {
        val hashMap = HashMap<String, Any>()
        params.forEach { (key, value) ->
            hashMap[key] = value
        }
        return hashMap
    }

    open fun getDate(): String {
        val simpleDateFormat = SimpleDateFormat("yyyyMMdd") // HH:mm:ss
        val date = Date(System.currentTimeMillis())
        return simpleDateFormat.format(date)
    }

    open fun getUseDate(): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd");// HH:mm:ss
        val date = Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }


    override fun onCreate() {

    }

    override fun onDestroy() {
    }
}