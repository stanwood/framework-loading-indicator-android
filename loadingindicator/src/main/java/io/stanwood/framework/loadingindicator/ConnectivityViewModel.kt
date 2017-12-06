package io.stanwood.framework.loadingindicator

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.TextUtils
import android.view.View

class ConnectivityViewModel(private val retryClickCallback: ItemClickCallback?,
                            private val genericErrorMessage: String,
                            private val showError: Boolean = false) : BaseObservable() {

    var loadingMessage: String? = null
        set(value) {
            field = value
            error = null
            notifyChange()
        }

    private var isLoading: Boolean = false

    var error: String? = null
        set(value) {
            field = value
            isLoading = !TextUtils.isEmpty(loadingMessage)
            notifyChange()
        }

    val isError: Boolean
        @Bindable
        get() = !TextUtils.isEmpty(error)

    val isVisible: Boolean
        @Bindable
        get() = isLoading || isError

    val message: String?
        @Bindable
        get() = if (isError) errorMessage else loadingMessage

    private val errorMessage: String?
        get() = if (showError) error else genericErrorMessage

    fun onClick(v: View) {
        retryClickCallback?.onClick(this)
    }

    interface ItemClickCallback {
        fun onClick(trigger: ConnectivityViewModel)
    }

}