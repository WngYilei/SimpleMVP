package com.xl.base.mvp.view

import com.xl.base.mvp.contract.IPresenterContract

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
interface IBaseView<out P : IPresenterContract> {
    fun registerPresenter(): Class<out P>
}