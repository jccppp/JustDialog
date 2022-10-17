package com.jccppp.justdialog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import com.jccppp.dialog.*
import com.jccppp.justdialog.databinding.DialogTestBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<View>(R.id.hw).setOnClickListener {
            JustDialog(this, TestDialogBehavior("你好啊"),com.jccppp.dialog.R.style.Transparent_Dialog)
                .addView(R.layout.dialog_test2)
                .setWidth(1f)
                .cancelOnTouchOutside(true)
                .showBottom()
//              .showGravity(gravity = Gravity.TOP)

          /*  JustDialog(this).addView(R.layout.dialog_test2).cancelOnTouchOutside(true).setText(R.id.test, "wo是123")
                .show()*/
        }

    }
}