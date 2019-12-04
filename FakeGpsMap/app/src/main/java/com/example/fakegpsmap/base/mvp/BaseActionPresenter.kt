package com.example.fakegpsmap.base.mvp

abstract class BaseActionPresenter<M : Any, T : BaseContract.ViewWithModel<M>>(var modelType: Class<M>) :
    BasePresenter<T>(), BaseContract.Presenter<T> {
    private fun runOnUiThread(action: () -> Unit) {
        action.invoke()
    }

    private var actionType1: ((T) -> Unit)? = null

    private var actionType2: ((T) -> Unit)? = null

    private var shouldClose: Boolean = false

    private var shouldShowContent: Boolean = false

    protected lateinit var model: M

    private var modelInitialized = false

    protected val RESPONSE_CODE_SUCCESS = 200
    protected val RESPONSE_CODE_500 = 500

    override fun init(isRestoring: Boolean) {
        super.init(isRestoring)
        model = createModel()
        doInit(isRestoring)
    }

    open protected fun createModel(): M = modelType.newInstance()

    protected abstract fun doInit(isRestoring: Boolean)

    protected fun updateViewData(m: M) {
        model = m
        modelInitialized = true
        runOnUiThread {
            if (view != null) {
                view!!.showContent(model)
            } else {
                shouldShowContent = true
            }
        }
    }

    protected fun updateViewActionType1(action: (view: T) -> Unit) =
        runOnUiThread {
            if (view != null) {
                action(view!!)
            } else {
                actionType1 = action
            }
        }

    protected fun updateViewActionType2(action: (view: T) -> Unit) =
        runOnUiThread {
            if (view != null) {
                action(view!!)
            } else {
                actionType2 = action
            }
        }

    protected fun closeView() =
        runOnUiThread {
            if (view != null) {
                view!!.close()
            } else {
                shouldClose = true
            }
        }

    override fun attachView(view: T) {
        if (isAttachView) {
            return
        }
        super.attachView(view)
        onSyncViewState()
    }

    private fun onSyncViewState() {
        if (shouldClose) {
            view!!.close()
        } else {
            if (shouldShowContent) {
                view!!.showContent(model)
                shouldShowContent = false
            }

            if (actionType1 != null) {
                actionType1!!(view!!)
                actionType1 = null
            }

            if (actionType2 != null) {
                actionType2!!(view!!)
                actionType2 = null
            }
        }
    }

    override fun syncViewOnAttach() {
        if (modelInitialized) {
            shouldShowContent = true
        }
    }
}
