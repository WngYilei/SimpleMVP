package com.xl.base.mvp.presenter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import androidx.fragment.app.Fragment
import com.xl.base.mvp.presenter.IBasePresenter

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
@SuppressLint("UseRequireInsteadOfGet")
fun IBasePresenter<*, *>.getContextEx(): Context = when {
    getMvpView() is Activity -> getMvpView() as Activity
    getMvpView() is Fragment -> (getMvpView() as Fragment).activity!!
    else -> throw IllegalStateException("the presenter not found context")
}