package io.stanwood.framework.loadingindicator

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

/**
 * The view for the loading indicator.
 */
class LoadingIndicatorView @JvmOverloads constructor(context: Context,
                                                     attrs: AttributeSet? = null,
                                                     defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    companion object {
        private val MIN_SHOW_TIME = 500
        private val MIN_DELAY = 200
    }

    private var isExpanded = false
    private var startTime = -1L
    private var isPostedHide = false
    private var isPostedShow = false
    private var isDismissed = false

    private val delayedHide = {
        isPostedHide = false
        startTime = -1
        animateState(false)
    }
    private val delayedShow = {
        isPostedShow = false
        if (!isDismissed) {
            startTime = System.currentTimeMillis()
            animateState(true)
        }
    }

    init {
        pivotY = 0f
        scaleY = 0f
    }

    private fun animateState(expand: Boolean) {
        if (expand && !isExpanded) {
            animate().scaleY(1f).setDuration(150).start()
            isExpanded = true
        } else if (!expand && isExpanded) {
            animate().scaleY(0f).setDuration(150).start()
            isExpanded = false
        }
    }

    public override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeCallbacks()
    }

    private fun removeCallbacks() {
        removeCallbacks(delayedHide)
        removeCallbacks(delayedShow)
    }

    /**
     * Animates the loading indicator in/out from/to the top if after [MIN_DELAY] for at least
     * [MIN_SHOW_TIME].
     *
     * To avoid flickering a few restrictions have been set in place:
     *
     * If the view is set to be expanded and within the delay the method is called again with
     * `expanded = false` the nothing happens.
     *
     * If the view is set to be hidden within the `MIN_DELAY + MIN_SHOW_TIME` after it has been set
     * to be expanded the indicator will stay expanded until this time frame has passed.
     *
     * @param expanded if `true` the loading indicator will be animated in, if `false` it will be
     *          animated out if necessary
     */
    fun setExpanded(expanded: Boolean) {
        if (expanded) {
            startTime = -1
            isDismissed = false
            removeCallbacks(delayedHide)
            if (!isPostedShow) {
                postDelayed(delayedShow, MIN_DELAY.toLong())
                isPostedShow = true
            }
        } else {
            isDismissed = true
            removeCallbacks(delayedShow)
            val diff = System.currentTimeMillis() - startTime
            if (diff >= MIN_SHOW_TIME || startTime == -1L) {
                animateState(expand = false)
            } else {
                if (!isPostedHide) {
                    postDelayed(delayedHide, MIN_SHOW_TIME - diff)
                    isPostedHide = true
                }
            }
        }
    }
}