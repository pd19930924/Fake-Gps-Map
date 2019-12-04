package com.example.fakegpsmap.base.mvp

import android.content.Context
import androidx.annotation.UiThread

class BaseContract {
    interface ViewWithModel<in M> : View {
        fun showContent(model: M)
    }

    interface ViewWithPersistentModel<in M> :
        ViewWithModel<M> {
        fun updateModelFromView(model: M)
    }

    interface View {
        fun getContext(): Context?

        fun showLoading()

        fun hideLoading()

        fun close()
    }

    interface Presenter<in T : View> {

        fun init(isRestoring: Boolean)

        @UiThread
        fun attachView(view: T)

        @UiThread
        fun detachView()

        @UiThread
        fun onDestroy()
    }
}