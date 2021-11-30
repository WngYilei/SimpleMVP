package com.xl.base.mvp.presenter

import com.xl.base.mvp.contract.IModelContract
import com.xl.base.mvp.contract.IViewContract


interface IBasePresenter<out V : IViewContract, out M : IModelContract> {

    fun registerModel(): Class<out M>

    fun getMvpView(): V

    fun getModel(): M
}
