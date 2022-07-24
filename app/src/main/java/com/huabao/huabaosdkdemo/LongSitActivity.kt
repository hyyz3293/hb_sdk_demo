package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.w100.messages.Sedentary_monitor_config
import kotlinx.android.synthetic.main.activity_long_sit.*

class LongSitActivity : AppCompatActivity() {

    private var isCheck = false
    private var startHour = 0
    private var startMin = 0
    private var endHour = 0
    private var endMin = 0
    private var sedentary_min = 0
    private var target_steps = 0
    private var remind_interval_min = 0
    private var remind_count = 0

    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, LongSitActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_long_sit)
        initView()
    }

    private fun initView() {
        backTv.setOnClickListener {
            finish()
        }
        openCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isCheck = isChecked
        }

        startHourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                startHour = data.toInt()
            }

        })

        startMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                startMin = data.toInt()
            }

        })

        endHourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                endHour = data.toInt()
            }

        })

        endMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                endMin = data.toInt()
            }

        })

        longSitMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                sedentary_min = data.toInt()
            }

        })

        longStepMinTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                target_steps = data.toInt()
            }

        })

        longRemindTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                remind_interval_min = data.toInt()
            }

        })

        longCountTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                remind_count = data.toInt()
            }

        })


        setBtn.setOnClickListener {

            val sedentaryMonitorConfig = Sedentary_monitor_config()
            val sedentaryRule = Sedentary_monitor_config.Sedentary_rule()
            val sedentaryTimeSpan = Sedentary_monitor_config.Sedentary_timespan()
            sedentaryMonitorConfig.is_enable = if (isCheck) {
                1
            } else {
                0
            }
            sedentaryMonitorConfig.rule = sedentaryRule
            sedentaryMonitorConfig.timespan = sedentaryTimeSpan
            sedentaryTimeSpan.begin_hour = startHour
            sedentaryTimeSpan.begin_min = startMin
            sedentaryTimeSpan.end_hour = endHour
            sedentaryTimeSpan.end_min = endMin
            sedentaryRule.remind_count = remind_count
            sedentaryRule.remind_interval_min = remind_interval_min
            sedentaryRule.sedentary_min = sedentary_min
            sedentaryRule.target_steps = target_steps
            BaosWatchSdk.setSedentaryReminder(sedentaryMonitorConfig)
        }
    }
}