package com.xl.base.mvp.model

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class CallBack<T> {

    open fun onNext(data: T) {
    }

    open fun onError(t: Throwable) {
    }

}