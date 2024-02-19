package com.huabao.huabaosdkdemo

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.sdk.huabaoImpl.translate.TranslateCallback
import kotlinx.android.synthetic.main.activity_dial.*
import kotlinx.android.synthetic.main.activity_dial.clearBtn
import kotlinx.android.synthetic.main.activity_dial.showTv
import kotlinx.android.synthetic.main.activity_main.*
import me.rosuh.filepicker.config.FilePickerManager
import me.rosuh.filepicker.engine.ImageEngine
import me.rosuh.filepicker.filetype.RasterImageFileType
import java.io.File


class DialActivity : AppCompatActivity() {

    companion object {

        fun getCallIntent(context: Context) {
            val intent = Intent(context, DialActivity::class.java)
            context.startActivity(intent)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dial)
        initView()
    }

    private var otaFile ="storage/emulated/0/122.bin"

    private var imageFile =""

    private var dialFile =""

    private fun initView() {
        backTv.setOnClickListener { finish() }

        showTv.movementMethod = ScrollingMovementMethod.getInstance()

        clearBtn.setOnClickListener {
            showTv.setText("")
        }

        otaPickBtn.setOnClickListener {
//            FilePickerManager
//                .from(this)
//                .enableSingleChoice()
//                //.registerFileType(arrayListOf(OTAFileType()), true)
//                .forResult(200)
            FilePickerManager
                .from(this)
                .forResult(FilePickerManager.REQUEST_CODE)

        }

        otaBtn.setOnClickListener {
            if(otaFile.isNullOrEmpty()){
                Toast.makeText(this, "请选择OTA文件", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            BaosWatchSdk.translateFile(File(otaFile), 0, object : TranslateCallback {
                override fun onLoadFile(progress: Int) {
                    addShowText("正在加载文件：$progress ")
                    //WatchBindManager.getInstance().hasOtaStatus(applicationContext, true)
                }

                override fun onLoadFileFail() {
                    addShowText("加载文件失败")
                    //WatchBindManager.getInstance().hasOtaStatus(applicationContext, false)
                }

                override fun onWaitWatchStartTranslate() {
                    addShowText("等待手表启动传输文件")
                }

                override fun onTranslateStart() {
                    addShowText("加载完成，开始传输文件")
                    //WatchBindManager.getInstance().hasOtaStatus(applicationContext, true)
                }

                override fun onTransferProgress(progress: Int) {
                    addShowText("传输文件：${progress}%")
                }

                override fun onTransferFinish() {
                    addShowText("传输结束")
                    //WatchBindManager.getInstance().hasOtaStatus(applicationContext, true)
                }

                override fun onTransferFail(errorCode: Int) {
                    addShowText("传输失败：${errorCode}")
                    //WatchBindManager.getInstance().hasOtaStatus(applicationContext, false)
                }

            })
        }

        dialPickBtn.setOnClickListener {
            FilePickerManager
                .from(this)
                .enableSingleChoice()
                .registerFileType(arrayListOf(RasterImageFileType()), true)
                .forResult(100)
        }

        generateDialBtn.setOnClickListener {
                if(imageFile.isNullOrEmpty()){
                    Toast.makeText(this,"请选择图片",Toast.LENGTH_LONG).show()
                    return@setOnClickListener
                }
            BaosWatchSdk.getWatchInfo {
                dialFile = BaosWatchSdk.generateWallBin(it,imageFile,true,Color.RED,Color.BLUE,0)
            }

        }

        transDialBtn.setOnClickListener {
            if(dialFile.isNullOrEmpty()){
                Toast.makeText(this,"请选择图片先生成壁纸文件",Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            BaosWatchSdk.translateFile(File(dialFile),4, object : TranslateCallback {
                override fun onLoadFile(progress: Int) {
                    addShowText("正在加载文件：$progress ")
                }

                override fun onLoadFileFail() {
                    addShowText("加载文件失败")
                }

                override fun onWaitWatchStartTranslate() {
                    addShowText("等待手表启动传输文件")
                }

                override fun onTranslateStart() {
                    addShowText("加载完成，开始传输文件")
                }

                override fun onTransferProgress(progress: Int) {
                    addShowText("传输文件：${progress}%")
                }

                override fun onTransferFinish() {
                    addShowText("传输结束")
                }

                override fun onTransferFail(errorCode: Int) {
                    addShowText("传输失败：${errorCode}")
                }

            })
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            100 -> {
                if (resultCode == RESULT_OK && data != null) {
                    val list = FilePickerManager.obtainData()
                    if (!list.isNullOrEmpty()) {
                        imageFile = list[0]
                    }
                }
            }
            200 -> {
                if (resultCode == RESULT_OK && data != null) {
                    val list = FilePickerManager.obtainData()
                    ///storage/emulated/0/122.bin https___blog.csdn.net_qq_44203816_article_details_124850092.png
                    if (!list.isNullOrEmpty()) {
                        otaFile = list[0]
                    }
                }
            }
        }
    }

    private fun addShowText(msg: String) {
        runOnUiThread {
            showTv.append(msg)
            showTv.append("\n")
        }

    }
}