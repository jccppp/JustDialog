package com.jccppp.dialog

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @Author      :zwz
 * @Date        :  2022/9/28 15:43
 * @Description : 描述
 */
interface JustDialogBehavior {

    fun createView(
        layoutInflater: LayoutInflater
    ): ViewGroup

    fun getDialogLayout(root: ViewGroup): JustDialogLayout

    fun getDialog(dialog:JustDialog)

}