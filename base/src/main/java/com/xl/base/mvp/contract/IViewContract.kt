package com.xl.base.mvp.contract

import android.content.Context
import com.xl.base.mvp.dialog.BaseDialog

/**
 * @author: wyl
 * @date:  2020-12-7
 * @version: 1.0.0
 * @describe:  TODO
 */
interface IViewContract {

    fun showToast(resId: Int)

    fun showToast(text: String?)

    fun goActivity(cls: Class<*>)

    fun startService(ctx: Context, cls: Class<*>)

    fun runOnUi(ui: () -> Unit)

    fun goActivity(cls: Class<*>, vararg pairs: Pair<String, Any>)

    fun showDialog(dialog: BaseDialog)

    fun finish()

}