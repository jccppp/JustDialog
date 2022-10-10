package com.jccppp.dialog

import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.OnLifecycleEvent
import androidx.viewbinding.ViewBinding

/**
 * @Author      :zwz
 * @Date        :  2021/10/28 17:15
 * @Description : 描述
 */

fun JustDialog.addView(
    @LayoutRes viewRes: Int
): JustDialog {
    this.view.addCustomView(
        res = viewRes, null
    )
    return this
}

fun JustDialog.addView(
    view: View
): JustDialog {
    this.view.addCustomView(
        null,
        view = view
    )
    return this
}

// ViewBinding  ViewBinding::inflate 不需要addView
fun <VB : ViewBinding> JustDialog.bindView(
    onBind: (LayoutInflater) -> VB,
    bind: (VB) -> Unit
): JustDialog {
    val b = onBind(layoutInflater)
    addView(b.root)
    bind.invoke(b)
    return this
}

//要先addView才可以onBindView    ViewBinding::bind
fun <VB : ViewBinding> JustDialog.onBindView(
    onBind: (View) -> VB,
    bind: (VB) -> Unit
): JustDialog {
    bind.invoke(onBind(view.customView))
    return this
}

fun JustDialog.setClickDismiss(vararg clickId: Int): JustDialog {
    clickId.forEach {
        findView<View>(it)?.setOnClickListener {
            dismiss()
        }
    }
    return this
}

fun <T : View> JustDialog.findView(clickId: Int): T? = this.view.findViewById(clickId)

inline fun JustDialog.setClick(
    clickId: Int,
    time: Long = 500,
    dismiss: Boolean = false,
    crossinline block: (View) -> Unit
): JustDialog {
    findView<View>(clickId)?.setOnClickListener(object : OnDoubleClickListener(time) {
        override fun onDebouncingClick(v: View?) {
            block.invoke(v!!)
            if (dismiss) dismiss()
        }
    })
    return this
}

fun JustDialog.setGone(clickId: Int, gone: Boolean): JustDialog {
    findView<View>(clickId)?.visibility =
        if (gone) View.GONE else View.VISIBLE
    return this
}

fun JustDialog.setText(viewId: Int, context: String?): JustDialog {
    if (!context.isNullOrEmpty()) {
        findView<TextView>(viewId)?.text = context
    }
    return this
}

fun JustDialog.getCustomView(): View {
    return this.view
}


fun JustDialog.lifecycleOwner(owner: LifecycleOwner? = null): JustDialog {
    val owner = owner ?: windowContext
    owner.lifecycle.addObserver(DialogLifecycleObserver(::dismiss))
    return this
}

internal class DialogLifecycleObserver(private val dismiss: () -> Unit) : LifecycleObserver {
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy() = dismiss()
}
