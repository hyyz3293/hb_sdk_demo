package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.w100.messages.Regular_remind_config
import kotlinx.android.synthetic.main.activity_regular_remind.*

class RegularRemindActivity : AppCompatActivity() {

    private var remind_item = 0
    private var isCheck = false
    private var interval_sec = 0
    private var begin_hour = 0
    private var begin_min = 0
    private var end_hour = 0
    private var end_min = 0


    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, RegularRemindActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_regular_remind)
        initView()
    }

    private fun initView() {
        backTv.setOnClickListener {
            finish()
        }

        remindTypeTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                remind_item = data.toInt()
            }

        })

        openCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isCheck = isChecked
        }


        startHourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                begin_hour = data.toInt()
            }

        })

        startMinTv.addTextChangedListener(object : SimpleTextWatcher() {
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

        intervalTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                interval_sec = data.toInt()
            }

        })

        setBtn.setOnClickListener {
            val regular_remind_config = Regular_remind_config()
            val cfg = Regular_remind_config.Regular_remind_cfg()
            val timeSpan = Regular_remind_config.Regular_remind_cfg.Regular_timespan()
            timeSpan.begin_hour = begin_hour
            timeSpan.begin_min = begin_min
            timeSpan.end_hour = end_hour
            timeSpan.end_min = end_min
            cfg.interval_sec = interval_sec
            cfg.timespan = timeSpan
            regular_remind_config.remind_item = remind_item
            regular_remind_config.is_enable = if (isCheck) {
                1
            } else {
                0
            }
            regular_remind_config.cfg = cfg
            BaosWatchSdk.setRegularRemind(regular_remind_config)

        }

    }
}