package com.xl.simplemvp.api

import com.xl.simplemvp.dataclass.ArticleBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/article/list/{pageNo}/json")
    fun getArticle(@Path("pageNo") pageNo: Int): Flowable<ArticleBean>
}