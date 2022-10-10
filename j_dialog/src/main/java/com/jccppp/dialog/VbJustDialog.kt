package com.jccppp.dialog

import android.content.Context
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


open class VbJustDialog<Vb : ViewBinding> : JustDialog {

    constructor(
        context: FragmentActivity,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(
        context,
        dialogBehavior,
        themeResId = themeResId
    )

    constructor(
        context: Fragment,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(
        context.requireActivity(),
        dialogBehavior,
        themeResId = themeResId
    )

    constructor(
        windowContext: Context,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(windowContext, dialogBehavior, themeResId)

    private var _dBind: Vb = inflateBindingWithGeneric(layoutInflater)

    val dBind: Vb get() = _dBind

    init {
        addView(view = _dBind.root)
    }


    private fun <VB : ViewBinding> Any.inflateBindingWithGeneric(layoutInflater: LayoutInflater) =
        withGenericBindingClass<VB>(this) { clazz ->
            clazz.getMethod("inflate", LayoutInflater::class.java)
                .invoke(null, layoutInflater) as VB
        }

    private  val Any.allParameterizedType: List<ParameterizedType>
        get() {
            val genericParameterizedType = mutableListOf<ParameterizedType>()
            var genericSuperclass = javaClass.genericSuperclass
            var superclass = javaClass.superclass
            while (superclass != null) {
                if (genericSuperclass is ParameterizedType) {
                    genericParameterizedType.add(genericSuperclass)
                }
                genericSuperclass = superclass.genericSuperclass
                superclass = superclass.superclass
            }
            return genericParameterizedType
        }

    private fun <VB : ViewBinding> withGenericBindingClass(any: Any, block: (Class<VB>) -> VB): VB {
        any.allParameterizedType.forEach { parameterizedType ->
            parameterizedType.actualTypeArguments.forEach {
                try {
                    return block.invoke(it as Class<VB>)
                } catch (e: NoSuchMethodException) {
                } catch (e: ClassCastException) {
                }
            }
        }
        throw IllegalArgumentException("There is no generic of ViewBinding.")
    }

}