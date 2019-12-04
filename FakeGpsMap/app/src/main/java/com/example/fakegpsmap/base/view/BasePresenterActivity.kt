package com.example.fakegpsmap.base.view

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fakegpsmap.base.mvp.BaseContract
import com.example.fakegpsmap.base.mvp.BasePresenter

abstract class BasePresenterActivity<V : BaseContract.View,P : BasePresenter<V>> :
    AppCompatActivity(), BaseContract.View {
    protected var presenter: P? = null
    private var progressDialog = ProgressDialogFragment.instance

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        presenter = createPresenter()
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    abstract fun createPresenter(): P

    override fun getContext(): Context? {
        return this
    }

    override fun showLoading() {
        if (supportFragmentManager.findFragmentByTag(PROGRESS_DIALOG) == null) {
            progressDialog.show(supportFragmentManager, PROGRESS_DIALOG)
        }
    }

    override fun hideLoading() {
        if (supportFragmentManager.findFragmentByTag(PROGRESS_DIALOG) != null) {
            progressDialog.dismissAllowingStateLoss()
        }
    }

    override fun close() {
    }

    companion object {
        const val PROGRESS_DIALOG = "PROGRESS_DIALOG"
    }
}