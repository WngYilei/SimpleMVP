package com.xl.simplemvp.model

import com.xl.base.mvp.model.BaseModel
import com.xl.base.net.ApiManager
import com.xl.simplemvp.api.ApiService
import com.xl.simplemvp.contract.IMainView
import com.xl.simplemvp.dataclass.ArticleBean
import io.reactivex.Flowable

class MainModel : BaseModel(), IMainView.IModel {
    val apiService = ApiManager.provideApiService(ApiService::class.java)
    override fun getData(page: Int): Flowable<ArticleBean> = apiService.getArticle(page)

}