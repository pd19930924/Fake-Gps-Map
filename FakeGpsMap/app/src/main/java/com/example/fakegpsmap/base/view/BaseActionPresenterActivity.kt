package com.example.fakegpsmap.base.view

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.fakegpsmap.base.mvp.BaseActionPresenter
import com.example.fakegpsmap.base.mvp.BaseContract

abstract class BaseActionPresenterActivity<M : Any, V : BaseContract.ViewWithModel<M>, P : BaseActionPresenter<M, V>>
    : BaseContract.ViewWithModel<M>, AppCompatActivity() {

    protected var presenter: P? = null
    private var progressDialog = ProgressDialogFragment.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        presenter = createPresenter()
        presenter?.attachView(this as V)
        super.onCreate(savedInstanceState)
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