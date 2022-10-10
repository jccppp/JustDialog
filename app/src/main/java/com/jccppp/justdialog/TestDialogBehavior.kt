package com.jccppp.justdialog

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.jccppp.dialog.*

//带标题和返回键
class TestDialogBehavior(private val title: String) : JustDialogBehavior {
    override fun createView(layoutInflater: LayoutInflater): ViewGroup {
        return layoutInflater.inflate(R.layout.include_title_back_dialog, null) as ViewGroup
    }

    lateinit var root: ViewGroup

    override fun getDialogLayout(root: ViewGroup): JustDialogLayout {
        this.root = root
        return root.findViewById(R.id.content)
    }

    override fun getDialog(dialog: JustDialog) {
        root.findViewById<View>(R.id.cancel).setOnClickListener {
            dialog.dismiss()
        }
        root.findViewById<TextView>(R.id.title).setText(title)
    }
}