package io.stanwood.framework.loadingindicator

import android.databinding.BaseObservable
import android.databinding.Bindable
import android.text.TextUtils
import android.view.View

/**
 * View model for [LoadingIndicatorView].
 *
 * This View model allows setting of a loading message and an error message. If one of these is set the
 * loading indicator will be shown.
 *
 * The latest change to errorMessage or loadingMessage (including setting the latter to to `null`
 * or empty) always overrides the current state.
 *
 * @param retryClickCallback [ItemClickCallback] the callback to be invoked when the user clicks the
 *          retry button on the errorMessage
 */
class LoadingIndicatorViewModel(private val retryClickCallback: ItemClickCallback?) : BaseObservable() {

    /**
     * Changing the loading message directly affects the current state of the indicator. If `null`
     * or empty it will be hidden, else it will be shown, regardless of the current error state.
     */
    var loadingMessage: String? = null
        set(value) {
            field = value
            errorMessage = null
            notifyChange()
        }

    /**
     * Changing the error message directly affects the current state of the indicator except for
     * setting it to `null` while [loadingMessage] is set - this will have no effect. Any other
     * value will force the loading indicator to switch to error state.
     */
    var errorMessage: String? = null
        set(value) {
            field = value
            notifyChange()
        }

    val message: String?
        @Bindable
        get() = if (isError) errorMessage else loadingMessage

    val isError: Boolean
        @Bindable
        get() = !TextUtils.isEmpty(errorMessage)

    private val isLoading: Boolean
        get() = !TextUtils.isEmpty(loadingMessage)

    val isVisible: Boolean
        @Bindable
        get() = isLoading || isError

    fun onClick(@Suppress("UNUSED_PARAMETER") v: View) {
        retryClickCallback?.onClick(this)
    }

    interface ItemClickCallback {
        fun onClick(trigger: LoadingIndicatorViewModel)
    }

}