package com.xl.simplemvp.view

import android.graphics.Color
import androidx.recyclerview.widget.LinearLayoutManager
import com.xl.base.adapter.image.ImageLoader
import com.xl.base.adapter.recycler.GridDividerItemDecoration
import com.xl.base.adapter.recycler.StableAdapter
import com.xl.base.adapter.recycler.createStableAdapter
import com.xl.base.mvp.act.BaseMvpAppCompatActivity
import com.xl.base.utils.dp
import com.xl.base.utils.onSmartRefreshCallback
import com.xl.simplemvp.R
import com.xl.simplemvp.contract.IMainView
import com.xl.simplemvp.dataclass.ArticleBean
import com.xl.simplemvp.item.ArticleItem
import com.xl.simplemvp.presnter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseMvpAppCompatActivity<IMainView.IPresenter>(), IMainView.IVew {

    override fun getLayoutId(): Int = R.layout.activity_main
    private var page = 0
    override fun registerPresenter(): Class<out IMainView.IPresenter> = MainPresenter::class.java
    private lateinit var recyclerAdapter: StableAdapter
    override fun initView() {
        super.initView()


        smartRefresh.autoRefresh()

        smartRefresh.onSmartRefreshCallback {
            onRefresh {
                page = 0
                getPresenter().getData(page)
            }
            onLoadMore {
                page += 1
                getPresenter().getData(page)
            }
        }
        recyclerAdapter = createStableAdapter {
            imageLoader = ImageLoader(baseContext)
        }

        recycle.apply {
            adapter = recyclerAdapter
            layoutManager =
                LinearLayoutManager(context).apply { orientation = LinearLayoutManager.VERTICAL }
            addItemDecoration(
                GridDividerItemDecoration(
                    0,
                    0.5.dp, Color.parseColor("#EEEEEE")
                )
            )
        }

        btn.setOnClickListener {
            getPresenter().getData(0)
        }
    }

    override fun getData(data: ArticleBean) {
        smartRefresh.finishLoadMore()
        smartRefresh.finishRefresh()
        val items = mutableListOf<ArticleItem>()
        data.datas.forEach { article ->
            items.add(ArticleItem(article))
        }
        data.datas.size.let { it1 ->
            recyclerAdapter.submitList(it1, items, page == 0)
        }
    }
}