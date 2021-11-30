package com.xl.base.net

import android.text.TextUtils
import io.reactivex.subscribers.DisposableSubscriber

/**
 * @author: wyl
 * @date:  2020/12/7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class SubscriberCompat<T> : DisposableSubscriber<T>() {
    abstract fun accept(data: T)
    abstract fun error(e: ApiError)
    override fun onComplete() {

    }

    override fun onError(t: Throwable?) {
        if (isDisposed) return
        val errMsg: String
        val cause = t?.cause

        if (cause is ApiError) {
            errMsg = cause.errMsg
            error(cause)
        } else {
            errMsg = "未知错误"
            error(ApiError(-1, errMsg, t))
        }
        if (!TextUtils.isEmpty(errMsg) && needShowMsg()) {

        }
    }

    override fun onNext(t: T) {
        if (isDisposed) return
        accept(t)
    }

    open fun needShowMsg() = false

}