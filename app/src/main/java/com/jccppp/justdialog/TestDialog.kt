package com.jccppp.justdialog

import android.content.Context
import com.jccppp.dialog.VbJustDialog
import com.jccppp.justdialog.databinding.DialogTestBinding

/**
 * @Author      :zwz
 * @Date        :  2022/9/28 17:34
 * @Description : 描述
 */
class TestDialog(context: Context, str: String) : VbJustDialog<DialogTestBinding>(context) {

    init {
        dBind.test.text = str
    }
}