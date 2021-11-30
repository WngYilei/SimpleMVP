package com.zity.sunmi_t1.utils

import android.annotation.SuppressLint
import android.content.Intent
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

/**
 * @author: wyl
 * @date:  2020/12/23
 * @version: 1.0.0
 * @describe:  TODO
 */
object RxBus {
    private val bus = PublishSubject.create<Any>().toSerialized()
    private fun toObservable() = bus
    private fun post(any: Any) {
        bus.onNext(any)
    }

    fun postAction(action: String) {
        post(Intent(action))
    }

    @SuppressLint("CheckResult")
    fun toObservableIntent(block: (intent: Intent) -> Unit): Disposable {
        return  toObservable()
                .filter {
                    it is Intent
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    val busIntent = it as Intent
                    block(busIntent)

                }, {})
    }
}