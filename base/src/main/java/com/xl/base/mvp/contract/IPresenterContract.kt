package com.xl.base.mvp.contract

import android.content.Intent
import io.reactivex.disposables.Disposable

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
interface IPresenterContract {

    fun onCreate()

    fun onStart()

    fun onResume()

    fun onPause()

    fun onStop()

    fun onDestroy()

    fun register(mvpView: IViewContract)

    fun manageTask(task: Disposable)

    fun init(intent: Intent)

    fun onNewIntent(intent: Intent?)
}