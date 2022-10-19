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

/*
inline fun <reified VB : ViewBinding> JustDialog.binding() = lazy(LazyThreadSafetyMode.NONE) {
    inflateBinding<VB>(layoutInflater).also { setContentView(it.root) }
}

inline fun <reified VB : ViewBinding> inflateBinding(layoutInflater: LayoutInflater) =
    VB::class.java.getMethod("inflate", LayoutInflater::class.java)
        .invoke(null, layoutInflater) as VB

inline fun <reified VB : ViewBinding> bindBinding(view: View) =
    VB::class.java.getMethod("bind", View::class.java).invoke(null, view) as VB
*/

//要先addView才可以onBindView    ViewBinding::bind
inline fun <reified VB : ViewBinding> JustDialog.onBindView(
    bind: (VB) -> Unit
): JustDialog {
    val v = VB::class.java.getMethod("bind", View::class.java).invoke(null, view.customView) as VB
    bind.invoke(v)
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

fun <T : View> JustDialog.findView(clickId: Int): T? = this.rootView.findViewById(clickId)

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

fun JustDialog.getRootView(): View {
    return this.rootView
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
