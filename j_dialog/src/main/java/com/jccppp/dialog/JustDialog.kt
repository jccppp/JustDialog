package com.jccppp.dialog

/**
 * @Author      :zwz
 * @Date        :  2021/10/28 16:52
 * @Description : 描述
 */

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Point
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner

open class JustDialog : Dialog {

    lateinit var windowContext: FragmentActivity

    private lateinit var dialogBehavior: JustDialogBehavior

    constructor(
        context: FragmentActivity,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(context, themeResId) {
        init(context, dialogBehavior)
    }

    constructor(
        context: Fragment,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(context.requireContext(), themeResId) {
        init(context, dialogBehavior)
    }

    constructor(
        windowContext: Context,
        dialogBehavior: JustDialogBehavior = BaseDialogBehavior(),
        themeResId: Int = R.style.Just_Dialog
    ) : super(windowContext, themeResId) {
        init(windowContext, dialogBehavior)
    }

    private var _owner: LifecycleOwner? = null

    /** The root layout of the dialog. */
    lateinit var view: JustDialogLayout

    open fun getOwner(): LifecycleOwner = _owner!!

    private fun init(any: Any, dialogBehavior: JustDialogBehavior) {
        this.dialogBehavior = dialogBehavior
        if (any is FragmentActivity) {
            this.windowContext = any
            _owner = any
        } else if (any is Fragment) {
            this.windowContext = any.requireActivity()
            _owner = any.viewLifecycleOwner
        }
        lifecycleOwner(any as? LifecycleOwner)

        val layoutInflater = LayoutInflater.from(context)

        val rootView = dialogBehavior.createView(
            layoutInflater = layoutInflater
        )
        setContentView(rootView)

        this.view = dialogBehavior.getDialogLayout(rootView)

        dialogBehavior.getDialog(this)


    }


    private var mWidthScale = 0f

    private var mHeightScale = 0f

    private var gravity = Gravity.CENTER

    private val mAppScreenWidth: Int by lazy(LazyThreadSafetyMode.NONE) { getAppScreenWidth() }

    private val mApScreenHeight: Int by lazy(LazyThreadSafetyMode.NONE) { getAppScreenHeight() }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        if (mWidthScale == 0f && mHeightScale == 0f) {
            return
        }

        val width: Int = if (mWidthScale == 0f) {
            ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            (mAppScreenWidth * mWidthScale).toInt()
        }
        val height: Int = if (mHeightScale == 0f) {
            ViewGroup.LayoutParams.WRAP_CONTENT
        } else {
            (mApScreenHeight * mHeightScale).toInt()
        }
        val parent = view.parent as? ViewGroup
        parent?.let {
            val layoutParams = it.layoutParams
            layoutParams.width = width
            layoutParams.height = height
            it.layoutParams = layoutParams
        }
        window?.setLayout(width, height)
       /* window?.attributes?.let {
            it.width = width
            it.height = height
            window?.setAttributes(it)
        }*/

    }

    fun showGravity(gravity: Int) {
        setGravity(gravity)
        show()
    }

    override fun dismiss() {
        try {
            super.dismiss()
        } catch (t: Throwable) {
            t.printStackTrace()
        }
    }

    override fun show() {
        window?.setGravity(gravity)
        super.show()
    }

    fun showTop() {
        showGravity(Gravity.TOP)
    }

    fun showBottom() {
        showGravity(Gravity.BOTTOM)
    }

    fun setGravity(gravity: Int): JustDialog = apply {
        this.gravity = gravity
    }

    //设置宽度比例 0f~1f
    fun setWidth(width: Float): JustDialog = apply {
        mWidthScale = width
    }

    //设置高度比例 0f~1f
    fun setHeight(height: Float): JustDialog = apply {
        mHeightScale = height
    }


    //返回按键是否有效
    fun cancelable(cancelable: Boolean): JustDialog = apply {
        setCancelable(cancelable)
    }

    //关闭监听
    fun onDismissListener(listener: DialogInterface.OnDismissListener): JustDialog = apply {
        setOnDismissListener(listener)
    }

    //外部取消监听
    fun cancelOnTouchOutside(cancelable: Boolean): JustDialog = apply {
        setCanceledOnTouchOutside(cancelable)
    }


    fun getAppScreenWidth(): Int {
        val wm =
            windowContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return -1
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x
    }

    fun getAppScreenHeight(): Int {
        val wm =
            windowContext.getSystemService(Context.WINDOW_SERVICE) as? WindowManager ?: return -1
        val point = Point()
        wm.defaultDisplay.getSize(point)
        return point.y
    }


}