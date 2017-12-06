package io.stanwood.framework.loadingindicator

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout

class ConnectivityView @JvmOverloads constructor(context: Context,
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