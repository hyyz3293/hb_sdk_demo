package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.w100.messages.Not_disturb_config
import kotlinx.android.synthetic.main.activity_nodisturb.*
import kotlinx.android.synthetic.main.activity_nodisturb.backTv
import kotlinx.android.synthetic.main.activity_nodisturb.openCheckBox
import kotlinx.android.synthetic.main.activity_nodisturb.setBtn
import kotlinx.android.synthetic.main.activity_women_health.*

class NodisturbActivity : AppCompatActivity() {

    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, NodisturbActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var isCheck = false
    private var begin_hour = 0
    private var begin_min = 0
    private var end_hour = 0
    private var end_min = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nodisturb)
        initView()
    }

    private fun initView() {
        backTv.setOnClickListener {
            finish()
        }

        openCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isCheck = isChecked
        }

        beginHourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                begin_hour = data.toInt()
            }

        })

        beginMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {

                begin_min = data.toInt()
            }
        })

        endHourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                end_hour = data.toInt()
            }

        })

        endMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                end_min = data.toInt()
            }

        })

        setBtn.setOnClickListener {
            val not_disturb_config = Not_disturb_config()
            not_disturb_config.is_enable = if (isCheck) {
                1
            } else {
                0
            }
            not_disturb_config.begin_hour = begin_hour
            not_disturb_config.begin_min = begin_min
            not_disturb_config.end_hour = end_hour
            not_disturb_config.end_min = end_min
            BaosWatchSdk.setNotDisturb(not_disturb_config)
        }
    }
}