package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.sdk.entitiy.NotificationAppListEntity
import cn.baos.watch.w100.messages.AppSystemNotification
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_set_message.*
import kotlinx.android.synthetic.main.activity_set_message.clearBtn
import kotlinx.android.synthetic.main.activity_set_message.showTv

class SetMessageActivity : AppCompatActivity() {


    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, SetMessageActivity::class.java)
            context.startActivity(intent)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_set_message)
        initView()
    }

    private fun initView() {
        showTv.movementMethod = ScrollingMovementMethod.getInstance()

        clearBtn.setOnClickListener {
            showTv.text = ""
        }

        backTv.setOnClickListener {
            finish()
        }
        openNotifyBtn.setOnClickListener {
            BaosWatchSdk.setAllMessageNotifyEnable(true,this)
            addShowText("打开消息总开关")
        }

        closeNotifyBtn.setOnClickListener {
            BaosWatchSdk.setAllMessageNotifyEnable(false,this)
            addShowText("关闭消息总开关")
        }

        queryNotifyBtn.setOnClickListener {
           val list =  BaosWatchSdk.getNotificationAppList()
            addShowText("获取通知APP列表：")
            addShowText(getObjectString(list))
        }

        addNotifyBtn.setOnClickListener {
            //前提是该APP存在手机中
            BaosWatchSdk.addOneMessageNotify(NotificationAppListEntity("com.test1","test"))
            addShowText("添加通知APP，包名为com.test1，app名为test")
        }

        updateNotifyBtn.setOnClickListener {
            val app =  BaosWatchSdk.getNotificationAppList()[0]
            app.isChecked = false
            BaosWatchSdk.setOneMessageNotifyEnable(app)
            addShowText("修改APP"+app.appName+"通知开关为关状态")
        }
    }


    private fun getObjectString(any: Any?): String {
        return Gson().toJson(any)
    }

    private fun addShowText(msg: String) {
        runOnUiThread {
            showTv.append(msg)
            showTv.append("\n")
        }
    }
}