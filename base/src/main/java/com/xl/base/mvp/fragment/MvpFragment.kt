package com.xl.base.mvp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.xl.base.mvp.contract.IPresenterContract
import com.xl.base.mvp.contract.IViewContract
import com.xl.base.mvp.view.IBaseView

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class MvpFragment<out P : IPresenterContract> : Fragment(), IBaseView<P>, IViewContract {

    private var mPresenter: P? = null

    protected fun getPresenter() = mPresenter!!

    private fun initPresenter() {
        mPresenter = registerPresenter().newInstance()
        mPresenter?.register(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPresenter()
        mPresenter?.onCreate()
    }

    override fun onStart() {
        super.onStart()
        mPresenter?.onStart()
    }

    override fun onResume() {
        super.onResume()
        mPresenter?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mPresenter?.onPause()
    }

    override fun onStop() {
        super.onStop()
        mPresenter?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mPresenter != null) {
            mPresenter!!.onDestroy()
            mPresenter = null
        }
    }
}