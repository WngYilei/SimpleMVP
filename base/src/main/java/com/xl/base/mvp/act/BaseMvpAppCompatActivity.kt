package com.xl.base.mvp.act

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import com.xl.base.mvp.dialog.BaseDialog
import com.xl.base.utils.ToastUtils
import com.xl.base.mvp.contract.IPresenterContract

import kotlin.properties.Delegates


/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
abstract class BaseMvpAppCompatActivity<out P : IPresenterContract> : MvpAppCompatActivity<P>() {
    private var netType by Delegates.notNull<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        init(savedInstanceState)
        initView()
        initData()
        initListener()
    }


    /**
     * 使字体大小不随系统设置变化而变化
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        if (newConfig.fontScale != 1.toFloat()) {
            resources
        }
    }

    override fun getResources(): Resources {
        val resources = super.getResources()
        if (resources.configuration.fontScale != 1.toFloat()) {
            val configuration = Configuration()
            configuration.setToDefaults()
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return resources
    }


    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {

    }

    override fun onStart() {
        super.onStart()

    }


    protected open fun initView() {

    }

    protected open fun initListener() {

    }

    protected open fun initData() {
        getPresenter().init(intent)
    }

    override fun showToast(resId: Int) {
        ToastUtils.toast(this, resId)
    }

    override fun showToast(text: String?) {
        ToastUtils.toast(this, text)
    }

    override fun goActivity(cls: Class<*>) {
        val intent = Intent(this, cls)
        startActivity(intent)
    }

    override fun goActivity(cls: Class<*>, vararg pairs: Pair<String, Any>) {
        val intent = Intent(this, cls)
        pairs.forEach { (key, value) ->
            if (value is String)
                intent.putExtra(key, value)
            if (value is Int)
                intent.putExtra(key, value)
            if (value is Boolean)
                intent.putExtra(key, value)
        }
        startActivity(intent)
    }




    override fun startService(ctx: Context, cls: Class<*>) {
        val intent = Intent(ctx, cls)
        startService(intent)
    }


    override fun showDialog(dialog: BaseDialog) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(dialog, localClassName)
        transaction.commitAllowingStateLoss()
    }

    open fun click(v: View) {
        rxClick(v)
    }


    open fun rxClick(v: View) {

    }



    override fun runOnUi(ui: () -> Unit) {
        runOnUiThread {
            ui()
        }
    }


    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.decorView.systemUiVisibility =
                    (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_FULLSCREEN
                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == 131) {
            finish()
        }
        return super.onKeyUp(keyCode, event)
    }
}

