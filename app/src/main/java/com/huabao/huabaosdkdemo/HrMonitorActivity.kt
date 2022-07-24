package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.w100.messages.Health_measure_config
import kotlinx.android.synthetic.main.activity_hr_monitor.*

class HrMonitorActivity : AppCompatActivity() {


    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, HrMonitorActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var hrate_remind_max =0
    private var hrate_remind_min =0
    private var hrate_measure_interval =0
    private var hrate_measure_auto_save_value =0
    private var spo_measure_interval =0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hr_monitor)
        initView()
    }

    private fun initView() {
        backTv.setOnClickListener { finish() }

        remindMaxTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
                hrate_remind_max = data.toInt()
            }

        })

        remindMinTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
                hrate_remind_min = data.toInt()
            }

        })

        remindIntervalTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
                hrate_measure_interval = data.toInt()
            }

        })

        remindValueTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
                hrate_measure_auto_save_value = data.toInt()
            }

        })

        spoTv.addTextChangedListener(object :SimpleTextWatcher(){
            override fun onTextChanged(data: String) {
                spo_measure_interval = data.toInt()
            }

        })

        setBtn.setOnClickListener {
            val health_measure_config = Health_measure_config()
            health_measure_config.hrate_measure_auto_save_value = hrate_measure_auto_save_value
            health_measure_config.hrate_measure_interval = hrate_measure_interval
            health_measure_config.hrate_remind_max = hrate_remind_max
            health_measure_config.hrate_remind_min = hrate_remind_min
            health_measure_config.spo_measure_interval = spo_measure_interval
            BaosWatchSdk.setHealthMeasureConfig(health_measure_config)
        }
    }
}