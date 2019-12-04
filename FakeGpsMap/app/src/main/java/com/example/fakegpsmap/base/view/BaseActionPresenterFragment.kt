package com.example.fakegpsmap.base.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.fakegpsmap.base.mvp.BaseActionPresenter
import com.example.fakegpsmap.base.mvp.BaseContract

abstract class BaseActionPresenterFragment<M : Any, V : BaseContract.ViewWithModel<M>, P : BaseActionPresenter<M, V>> :
    Fragment(), BaseContract.ViewWithModel<M> {

    protected var presenter: P? = null
    private var progressDialog = ProgressDialogFragment.instance

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = createPresenter()
        presenter?.attachView(this as V)
    }

    override fun onDestroy() {
        presenter?.detachView()
        super.onDestroy()
    }

    abstract fun createPresenter(): P

    override fun showLoading() {
        var progressDialogFragment =
            childFragmentManager.findFragmentByTag(PROGRESS_DIALOG) as? ProgressDialogFragment
        if (progressDialogFragment == null) {
            progressDialog.show(childFragmentManager, PROGRESS_DIALOG)
        }
    }

    override fun hideLoading() {
        var progressDialogFragment =
            childFragmentManager.findFragmentByTag(PROGRESS_DIALOG) as? ProgressDialogFragment
        progressDialogFragment?.dismissAllowingStateLoss()
    }

    override fun showContent(model: M) {

    }

    protected fun isAttachActivity(): Boolean = activity != null

    companion object {
        const val PROGRESS_DIALOG = "PROGRESS_DIALOG"
    }
}