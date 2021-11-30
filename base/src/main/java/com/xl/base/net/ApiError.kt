package com.xl.base.net

data class ApiError(val errCode: Int, val errMsg: String, val th: Throwable?) : Throwable(errMsg, th)