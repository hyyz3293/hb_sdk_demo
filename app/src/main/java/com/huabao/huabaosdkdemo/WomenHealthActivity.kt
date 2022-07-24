package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.w100.messages.Menstrual_remind_config
import kotlinx.android.synthetic.main.activity_women_health.*

class WomenHealthActivity : AppCompatActivity() {

    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, WomenHealthActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var isCheck = false
    private var mens_remind_before_days = 0
    private var ovul_remind_before_days = 0
    private var remind_hour = 0
    private var remind_min = 0
    private var last_mens_year = 0
    private var last_mens_month = 0
    private var last_mens_day = 0
    private var mens_days = 0
    private var mens_period = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_women_health)

        initView()
    }

    private fun initView() {
        backTv.setOnClickListener {
            finish()
        }

        openCheckBox.setOnCheckedChangeListener { buttonView, isChecked ->
            isCheck = isChecked
        }
        preDayTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                mens_remind_before_days = data.toInt()
            }

        })

        pre1DayTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                ovul_remind_before_days = data.toInt()
            }

        })

        hourTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                remind_hour = data.toInt()
            }

        })

        minTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                remind_min = data.toInt()
            }

        })

        yearTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                last_mens_year = data.toInt()
            }

        })

        monthTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                last_mens_month = data.toInt()
            }

        })
        dayTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                last_mens_day = data.toInt()
            }

        })
        womenDayTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                mens_days = data.toInt()
            }

        })

        weekTv.addTextChangedListener(object : SimpleTextWatcher() {
            override fun onTextChanged(data: String) {
                mens_period = data.toInt()
            }

        })

        setBtn.setOnClickListener {
            val menstrual_remind_config = Menstrual_remind_config()
           val cfg =  Menstrual_remind_config.Menstrual_remind_cfg()
            menstrual_remind_config.is_enable = if (isCheck) {
                1
            } else {
                0
            }
            menstrual_remind_config.cfg =cfg
            cfg.last_mens_day = last_mens_day
            cfg.last_mens_month = last_mens_month
            cfg.last_mens_year = last_mens_year
            cfg.mens_days = mens_days
            cfg.mens_period = mens_period
            cfg.mens_remind_before_days = mens_remind_before_days
            cfg.ovul_remind_before_days = ovul_remind_before_days
            cfg.remind_hour = remind_hour
            cfg.remind_min = remind_min


            BaosWatchSdk.setMenstrualRemind(menstrual_remind_config)
        }
    }
}