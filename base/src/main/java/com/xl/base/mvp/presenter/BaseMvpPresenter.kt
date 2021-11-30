package com.xl.base.mvp.presenter

import android.content.Intent
import com.xl.base.mvp.contract.IModelContract
import com.xl.base.mvp.contract.IPresenterContract
import com.xl.base.mvp.contract.IViewContract

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class BaseMvpPresenter<out V : IViewContract, out M : IModelContract> : IBasePresenter<V, M>,
    IPresenterContract {

    private var mMVPView: V? = null
    private var mModel: M? = null
    private val mCompositeDisposable = CompositeDisposable()


    override fun manageTask(task: Disposable) {
//        mCompositeDisposable.clear()
        mCompositeDisposable.add(task)
    }

    open fun disposeAllTask() {
        mCompositeDisposable.dispose()
    }

    protected fun removeTask(task: Disposable) {
        mCompositeDisposable.remove(task)
    }

    @Suppress("UNCHECKED_CAST")
    override fun register(mvpView: IViewContract) {
        mMVPView = mvpView as V
        mModel = registerModel().newInstance()
    }

    override fun getMvpView() = mMVPView!!

    override fun getModel() = mModel!!

    override fun onCreate() {
        mModel!!.onCreate()
    }

    override fun onStart() {
    }

    override fun onResume() {

    }

    override fun onPause() {
    }

    override fun onStop() {

    }

    override fun onNewIntent(intent: Intent?) {

    }

    override fun init(intent: Intent) {

    }


    override fun onDestroy() {
        if (mMVPView != null) mMVPView = null
        if (mModel != null) {
            mModel!!.onDestroy()
            mModel = null
        }
        disposeAllTask()
    }

    fun showToast(msg: String) {
        getMvpView().showToast(msg)
    }

}
