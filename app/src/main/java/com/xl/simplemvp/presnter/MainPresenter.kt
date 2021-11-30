package com.xl.simplemvp.presnter

import com.xl.base.mvp.presenter.BaseMvpPresenter
import com.xl.base.net.ApiError
import com.xl.base.net.IoMainTransformer
import com.xl.base.net.SubscriberCompat
import com.xl.simplemvp.contract.IMainView
import com.xl.simplemvp.dataclass.ArticleBean
import com.xl.simplemvp.model.MainModel

class MainPresenter : BaseMvpPresenter<IMainView.IVew, IMainView.IModel>(), IMainView.IPresenter {
    override fun registerModel(): Class<out IMainView.IModel> = MainModel::class.java
    override fun getData(page :Int) {
        val task = getModel().getData(page).compose(IoMainTransformer.transformerFollowable())
            .subscribeWith(object : SubscriberCompat<ArticleBean>() {
                override fun accept(data: ArticleBean) {
                    getMvpView().getData(data)
                }

                override fun error(e: ApiError) {

                }

            })
        manageTask(task)
    }


}