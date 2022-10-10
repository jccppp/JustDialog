package com.jccppp.dialog

import android.view.LayoutInflater
import android.view.ViewGroup

/**
 * @Author      :zwz
 * @Date        :  2021/10/28 16:56
 * @Description : 描述
 */
class BaseDialogBehavior : JustDialogBehavior {

    override fun createView(
        layoutInflater: LayoutInflater
    ): ViewGroup {
        return layoutInflater.inflate(R.layout.include_just_dialog_father, null) as ViewGroup
    }

    override fun getDialogLayout(root: ViewGroup): JustDialogLayout {
        return root as JustDialogLayout
    }

    override fun getDialog(dialog: JustDialog) {

    }


}