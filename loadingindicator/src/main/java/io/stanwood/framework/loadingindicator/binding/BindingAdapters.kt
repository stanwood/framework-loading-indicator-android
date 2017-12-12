package io.stanwood.framework.loadingindicator.binding

import android.databinding.BindingAdapter
import android.view.View
import android.view.animation.Animation

@BindingAdapter("anim")
fun setAnimation(view: View, animation: Animation?) {
    view.apply {
        clearAnimation()
        if (animation != null) {
            startAnimation(animation)
        }
    }
}