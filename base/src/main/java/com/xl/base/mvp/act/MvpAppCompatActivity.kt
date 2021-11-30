package com.xl.base.mvp.act

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.xl.base.mvp.contract.IPresenterContract
import com.xl.base.mvp.contract.IViewContract
import com.xl.base.mvp.view.IBaseView


/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class MvpAppCompatActivity<out P : IPresenterContract> : AppCompatActivity(), IBaseView<P>,
    IViewContract {

    protected var TAG = this::class.java.name

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

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
        mPresenter?.onNewIntent(intent)
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