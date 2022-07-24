package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import kotlinx.android.synthetic.main.activity_remind.*
import kotlinx.android.synthetic.main.activity_remind.clearBtn
import kotlinx.android.synthetic.main.activity_remind.showTv

class RemindActivity : AppCompatActivity() {

    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, RemindActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remind)
        initView()
    }

    private fun initView() {
        backTv.setOnClickListener { finish() }
        showTv.movementMethod = ScrollingMovementMethod.getInstance()
        clearBtn.setOnClickListener {
            showTv.setText("")
        }

        titleTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
            }

        })
    }
}