package com.xl.base.mvp.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.xl.base.R
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BaseDialog : DialogFragment() {
    private var mCompositeDisposable: CompositeDisposable? = null

    protected fun manageTask(task: Disposable) {
        mCompositeDisposable?.add(task)
    }

    open fun disposeAllTask() {
        mCompositeDisposable?.dispose()
    }

    protected fun removeTask(task: Disposable) {
        mCompositeDisposable?.remove(task)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mCompositeDisposable = CompositeDisposable()
        setStyle(STYLE_NORMAL, R.style.dialog)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }


    protected abstract fun getLayoutId(): Int

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(true);
        dialog?.setCancelable(true);
        dialog?.window?.addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL);

        initView()
        initListener()


    }

    protected open fun initListener() {

    }

    protected open fun initView() {}

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(getWidthCompat(), getHeightCompat())
        dialog?.setCancelable(true);
        dialog?.setCanceledOnTouchOutside(true);
    }

    open fun getHeightCompat(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.heightPixels.times(0.8f).toInt()
    }

    open fun getWidthCompat(): Int {
        val displayMetrics = resources.displayMetrics
        return displayMetrics.widthPixels.times(0.8f).toInt()
    }

    override fun onDestroy() {
        mCompositeDisposable?.clear()
        disposeAllTask()
        mCompositeDisposable = null
        super.onDestroy()
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (manager.isDestroyed) return
        try {
            manager.beginTransaction().remove(this).commit()
            super.show(manager, tag)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}