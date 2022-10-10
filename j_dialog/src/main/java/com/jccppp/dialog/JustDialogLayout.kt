package com.jccppp.dialog


import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * @Author      :zwz
 * @Date        :  2021/10/28 16:53
 * @Description : 描述
 */

class JustDialogLayout(
    context: Context,
    attrs: AttributeSet?
) : FrameLayout(context, attrs) {

    lateinit var customView: View

    fun addCustomView(
        @LayoutRes res: Int?,
        view: View?
    ): View {
        if (view != null) {
            customView = view
            if (view.parent != null) {
                val parent = view.parent as? ViewGroup
                parent?.removeView(view)
            }
        } else if (res != null) {
            customView = LayoutInflater.from(context).inflate(res, this, false)
        }

        removeAllViews()
        addView(customView, LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT))


        return this
    }

}

