package com.xl.simplemvp.contract

import com.xl.base.mvp.contract.IModelContract
import com.xl.base.mvp.contract.IPresenterContract
import com.xl.base.mvp.contract.IViewContract
import com.xl.base.mvp.view.IBaseView
import com.xl.simplemvp.dataclass.ArticleBean
import io.reactivex.Flowable

interface IMainView {
    interface IVew : IViewContract {
        fun getData(data: ArticleBean)
    }

    interface IPresenter : IPresenterContract {
        fun getData(page: Int)
    }

    interface IModel : IModelContract {
        fun getData(page: Int): Flowable<ArticleBean>
    }
}