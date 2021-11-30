package com.xl.base.net

import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author: wyl
 * @date:  2020/12/8
 * @version: 1.0.0
 * @describe:  TODO
 */
class IoMainTransformer {
    companion object {
        fun <T> transformerFollowable() = FlowableTransformer<T, T> { t ->
            t.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
        }
    }
}