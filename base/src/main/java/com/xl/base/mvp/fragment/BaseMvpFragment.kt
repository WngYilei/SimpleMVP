package com.xl.base.mvp.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.xl.base.mvp.contract.IPresenterContract
import com.xl.base.mvp.dialog.BaseDialog

abstract class BaseMvpFragment<out P : IPresenterContract> : MvpFragment<P>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init(savedInstanceState)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
                getLayoutId(),
                container,
                false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initListener()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    protected abstract fun getLayoutId(): Int

    protected open fun init(savedInstanceState: Bundle?) {

    }

    protected open fun initView() {}

    protected open fun initData() {}
    protected open fun initListener() {

    }

    override fun showToast(resId: Int) {
        Toast.makeText(context, resId, Toast.LENGTH_SHORT).show()
    }

    override fun showToast(text: String?) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }


    override fun goActivity(cls: Class<*>) {
        startActivity(Intent(context, cls))
    }

    override fun goActivity(cls: Class<*>, vararg pairs: Pair<String, Any>) {
        val intent = Intent(context, cls)
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

    override fun showDialog(dialog: BaseDialog) {
        val transaction = childFragmentManager.beginTransaction()
        transaction.add(dialog, javaClass.simpleName)
        transaction.commitAllowingStateLoss()
    }

    override fun finish() {

    }



    override fun runOnUi(ui: () -> Unit) {
        activity?.runOnUiThread {
            ui()
        }
    }

    override fun startService(ctx: Context, cls: Class<*>) {
        startService(ctx, cls)
    }

    override fun onPause() {
        super.onPause()
    }

}