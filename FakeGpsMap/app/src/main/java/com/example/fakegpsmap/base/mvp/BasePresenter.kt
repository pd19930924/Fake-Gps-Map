package com.example.fakegpsmap.base.mvp

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter<T : BaseContract.View> :
    BaseContract.Presenter<T> {
    protected var view: T? = null
    private var compositeDisposable: CompositeDisposable? = null

    override fun init(isRestoring: Boolean) {
    }

    val isAttachView: Boolean
        get() = view != null

    override fun attachView(view: T) {
        this.view = view
        compositeDisposable = CompositeDisposable()
    }

    override fun detachView() {
        compositeDisposable?.clear()
        compositeDisposable = null
        this.view = null
    }

    override fun onDestroy() {
        compositeDisposable?.clear()
        compositeDisposable = null
    }

    protected fun addSubscription(subscription: Disposable) {
        compositeDisposable?.add(subscription)
    }

    protected fun addViewAttachSubscription(subscription: Disposable) {
        if (isAttachView) {
            compositeDisposable?.add(subscription)
        }
    }
    abstract fun syncViewOnAttach()
}