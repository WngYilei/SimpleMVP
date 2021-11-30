package com.xl.base

import android.app.Activity
import android.content.Context
import android.os.SystemClock
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileInputStream
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun String.handCardNo(): String {
    val stringBuffer = StringBuffer()
    if (length <= 9) {
        for (i in 0 until 10 - length) {
            stringBuffer.append("0")
        }
    }
    return stringBuffer.toString() + toString()
}



fun isMoney(str: String): Boolean {
    val pattern: Pattern = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$") // 判断小数点后2位的数字的正则表达式
    val match: Matcher = pattern.matcher(str)
    return match.matches()
}


fun String.formatPhone(): String {
    if (isEmpty()) return ""
    return substring(0, 3) + "****" + substring(7, 11)
}


fun getJsonMap(vararg params: Pair<String, Any>): HashMap<String, Any> {
    val hashMap = HashMap<String, Any>()
    params.forEach { (key, value) ->
        hashMap[key] = value
    }
    return hashMap
}


fun String.toMD5(): String {
    val messageDigest = MessageDigest.getInstance("MD5")
    val hash = messageDigest.digest(toByteArray())
    val hex = StringBuilder(hash.size * 2)
    hash.forEach {
        val b = it.toInt()
        if ((b and 0xFF) < 0x10) hex.append("0")
        hex.append(Integer.toHexString(b and 0xFF))
    }
    return hex.toString()
}

fun File.toMD5(): String {
    val fis = FileInputStream(this)
    try {
        val messageDigest = MessageDigest.getInstance("MD5")
        val buffer = ByteArray(8192)
        while (true) {
            val length = fis.read(buffer)
            if (length != -1) {
                messageDigest.update(buffer, 0, length)
            } else {
                break
            }
        }
        val hash = messageDigest.digest()
        val hex = StringBuilder(hash.size * 2)
        hash.forEach {
            val b = it.toInt()
            if ((b and 0xFF) < 0x10) hex.append("0")
            hex.append(Integer.toHexString(b and 0xFF))
        }
        return hex.toString()
    } catch (e: Exception) {

    } finally {
        fis.close()
    }
    return ""
}

fun Activity.getColorCompat(resId: Int) = ContextCompat.getColor(this, resId)


fun Context.scalingWidth(pixelWidth: Int, pixelHeight: Int, referenceHeight: Int): Int {
    return pixelWidth * referenceHeight / pixelHeight
}

fun Context.scalingHeight(pixelWidth: Int, pixelHeight: Int, referenceWidth: Int): Int {
    return referenceWidth * pixelHeight / pixelWidth
}

fun Context.scalingCardHeight(referenceWidth: Int): Int {
    return referenceWidth * 360 / 672
}

fun Activity.getStringCompat(@StringRes id: Int): String {
    return resources.getString(id)
}

fun getSimpleUUID(): String {
    return "${SystemClock.uptimeMillis()}-${System.currentTimeMillis()}-${Random().nextInt(10000)}".toMD5()
}


fun RecyclerView.setupVertical() {
    setHasFixedSize(true)
    val layoutManager = LinearLayoutManager(context)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = layoutManager
}


fun getDate(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy/MM/dd") // HH:mm:ss
    val date = Date(System.currentTimeMillis())
    return simpleDateFormat.format(date)
}

fun getDate2(): String {
    val simpleDateFormat = SimpleDateFormat("HH:mm") // HH:mm:ss
    val date = Date(System.currentTimeMillis())
    return simpleDateFormat.format(date)
}


fun getDateTime(): String {
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss") // HH:mm:ss
    val date = Date(System.currentTimeMillis())
    return simpleDateFormat.format(date)
}

fun getLongDateTime(time: String): Long {
    val instance = Calendar.getInstance(Locale.CHINA)
    val year = instance.get(Calendar.YEAR)
    val month = instance.get(Calendar.MONTH) + 1
    val day = instance.get(Calendar.DAY_OF_MONTH)
    val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA)
    return simpleDateFormat.parse("$year-$month-$day $time").time
}


fun RecyclerView.setupHorizontal() {
    setHasFixedSize(true)
    val layoutManager = LinearLayoutManager(context)
    layoutManager.orientation = LinearLayoutManager.HORIZONTAL
    this.layoutManager = layoutManager
}

fun RecyclerView.setupGrid(spanCount: Int) {
    setHasFixedSize(true)
    val layoutManager = GridLayoutManager(context, spanCount)
    this.layoutManager = layoutManager
}

//returns dip(dp) dimension value in pixels
fun Context.dip(value: Int): Int = (value * resources.displayMetrics.density).toInt()

fun Context.dip(value: Float): Int = (value * resources.displayMetrics.density).toInt()


