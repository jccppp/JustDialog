package com.jccppp.justdialog

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.jccppp.dialog.JustDialog
import com.jccppp.dialog.addView
import com.jccppp.dialog.onBindView
import com.jccppp.dialog.setText
import com.jccppp.justdialog.databinding.DialogTest2Binding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.hw).setOnClickListener {
            JustDialog(this,TestDialogBehavior("123"))
                .addView(R.layout.dialog_test2)
                .setText(R.id.test, "asdasd")
                .setHeight(0.5f)
                .onBindView<DialogTest2Binding> {
                    val context = it.test.context
                    println(context)
                    it.test.setOnClickListener { v ->
                        it.test.text = "wowwow"
                    }
                }
                .setWidth(1f)
                .cancelOnTouchOutside(true)
                .showBottom()
//              .showGravity(gravity = Gravity.TOP)

            /*  JustDialog(this).addView(R.layout.dialog_test2).cancelOnTouchOutside(true).setText(R.id.test, "wo是123")
                  .show()*/
        }

    }
}