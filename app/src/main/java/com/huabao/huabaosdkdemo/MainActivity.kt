package com.huabao.huabaosdkdemo

import android.Manifest
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.sdk.api.ConnectListener
import cn.baos.watch.sdk.api.DeviceCallBack
import cn.baos.watch.sdk.api.SyncDataListener
import cn.baos.watch.sdk.entitiy.WeatherEntity
import cn.baos.watch.w100.messages.*
import com.google.gson.Gson
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private val rxPermission by lazy {
        RxPermissions(this)
    }

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)

    private val startTime by lazy {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_MONTH, -2)
        calendar.time
    }

    private val endTime by lazy {
        val calendar = Calendar.getInstance()
        calendar.time
    }

    companion object {


        private val EXTRA_MAC = "EXTRA_MAC"

        fun getCallIntent(context: Context, mac: String) {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_MAC, mac)
            context.startActivity(intent)
        }
    }

    private var macAddress = ""

    private val optionButtons: MutableList<OptionButton> by lazy {
        val buttons = mutableListOf<OptionButton>()
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_connect),
                OptionButton.TYPE_CONNECT
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_disconnect),
                OptionButton.TYPE_DISCONNECT
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_bind_device),
                OptionButton.TYPE_BIND_DEVICE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_unbind_device),
                OptionButton.TYPE_UNBIND_DEVICE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_get_watch_info),
                OptionButton.TYPE_GET_WATCH_INFO
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_get_battery),
                OptionButton.TYPE_GET_BATTERY
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_user),
                OptionButton.TYPE_SET_USER
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_time),
                OptionButton.TYPE_SET_TIME
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_time_foramte12),
                OptionButton.TYPE_SET_TIME_FORMAT12
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_time_foramte24),
                OptionButton.TYPE_SET_TIME_FORMAT24
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_open_ligth),
                OptionButton.TYPE_OPEN_LIGHT_SCREEN
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_close_light),
                OptionButton.TYPE_CLOSE_LIGHT_SCREEN
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_long_sit),
                OptionButton.TYPE_SET_LONG_SIT
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_regular_remind),
                OptionButton.TYPE_SET_REGULAR_REMIND
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_women_health),
                OptionButton.TYPE_SET_WOMEN_HEALTH
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_no_disturb),
                OptionButton.TYPE_SET_NO_DISTURB
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_hr_monitor),
                OptionButton.TYPE_SET_HR_MONITOR
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_message),
                OptionButton.TYPE_SET_MESSAGE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_weather),
                OptionButton.TYPE_SET_WEATHER
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_gps),
                OptionButton.TYPE_SET_GPS
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_set_dial_option),
                OptionButton.TYPE_SET_DIAL
            )
        )

        buttons.add(
            OptionButton(
                resources.getString(R.string.str_sync_data),
                OptionButton.TYPE_SYNC_DATA
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_get_realtime_data),
                OptionButton.TYPE_GET_REAL_DAILY_DATA
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_get_realtime_hr_data),
                OptionButton.TYPE_GET_REAL_HR_DATA
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_get_realtime_spo_data),
                OptionButton.TYPE_GET_REAL_SPO_DATA
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_daily_sum),
                OptionButton.TYPE_QUERY_DAILY_SUM
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_daily_hr),
                OptionButton.TYPE_QUERY_DAILY_HR
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_sleep_sum),
                OptionButton.TYPE_QUERY_SLEEP_SUM
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_sport_data),
                OptionButton.TYPE_QUERY_SPORT
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_reset_hr),
                OptionButton.TYPE_QUERY_RESET_HR
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_op),
                OptionButton.TYPE_QUERY_OP
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_sport_mode),
                OptionButton.TYPE_QUERY_SPORT_MODE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_sport_hr),
                OptionButton.TYPE_QUERY_SPORT_HR
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_query_sleep_data),
                OptionButton.TYPE_QUERY_SLEEP_DATA
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_find_phone),
                OptionButton.TYPE_FIND_PHONE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_close_find_phone),
                OptionButton.TYPE_CLOSE_FIND_PHONE
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_open_music),
                OptionButton.TYPE_OPEN_MUSIC
            )
        )
        buttons.add(
            OptionButton(
                resources.getString(R.string.str_close_music),
                OptionButton.TYPE_CLOSE_MUSIC
            )
        )
        buttons
    }

    private val optionAdapter by lazy {
        OptionAdapter(optionButtons)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initView()
        initSdkFun()
    }


    private fun initView() {
        macAddress = intent.getStringExtra(EXTRA_MAC) ?: ""
        deviceMacTv.text = macAddress

        showTv.movementMethod = ScrollingMovementMethod.getInstance()

        clearBtn.setOnClickListener {
            showTv.text = ""
        }

        optionRecyclerView.layoutManager = GridLayoutManager(this, 3)
        optionRecyclerView.adapter = optionAdapter

        optionAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (position >= 0 && position < optionButtons.size) {
                when (optionButtons[position].type) {
                    OptionButton.TYPE_CONNECT -> {
                        BaosWatchSdk.connectDevice(macAddress)
                    }
                    OptionButton.TYPE_SET_USER->{
                        val user_info_config = User_info_config()
                        user_info_config.birth_day = 1
                        user_info_config.birth_year = 91
                        user_info_config.birth_month =3
                        user_info_config.gender =1
                        user_info_config.height_cm=175
                        user_info_config.user_name ="test"
                        user_info_config.weight_kg = 65
                        BaosWatchSdk.setUserInfo(user_info_config)
                    }
                    OptionButton.TYPE_DISCONNECT -> {
                        BaosWatchSdk.disConnectDevice()
                    }
                    OptionButton.TYPE_BIND_DEVICE -> {
                        BaosWatchSdk.bindDevice("12") {
                            if (it) {
                                addShowText(resources.getString(R.string.bind_device_success))
                            } else {
                                addShowText(resources.getString(R.string.bind_device_fail))
                            }
                        }
                    }
                    OptionButton.TYPE_UNBIND_DEVICE -> {
                        rxPermission.request(
                            Manifest.permission.READ_PHONE_STATE
                        ).subscribe {
                            if (it) {
                                BaosWatchSdk.unBindDevice("12")
                            } else {

                            }
                        }

                    }
                    OptionButton.TYPE_GET_WATCH_INFO -> {
                        BaosWatchSdk.getWatchInfo {
                            addShowText(getObjectString(it))
                        }
                    }
                    OptionButton.TYPE_GET_BATTERY -> {
                        BaosWatchSdk.getBatteryInfo {
                            addShowText(getObjectString(it))
                        }
                    }

                    OptionButton.TYPE_SET_TIME -> {
                        BaosWatchSdk.setTime(System.currentTimeMillis(),0)
                    }
                    OptionButton.TYPE_SET_TIME_FORMAT12 -> {
                        BaosWatchSdk.setTimeFormat(0)
                    }
                    OptionButton.TYPE_SET_TIME_FORMAT24 -> {
                        BaosWatchSdk.setTimeFormat(1)
                    }
                    OptionButton.TYPE_OPEN_LIGHT_SCREEN -> {
                        BaosWatchSdk.setRaiseLightUpSwitch(true)
                    }
                    OptionButton.TYPE_CLOSE_LIGHT_SCREEN -> {
                        BaosWatchSdk.setRaiseLightUpSwitch(false)
                    }
                    OptionButton.TYPE_SET_LONG_SIT -> {
                        LongSitActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_REGULAR_REMIND -> {
                        RegularRemindActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_WOMEN_HEALTH -> {
                        WomenHealthActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_NO_DISTURB -> {
                        NodisturbActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_HR_MONITOR -> {
                        HrMonitorActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_MESSAGE -> {
                        SetMessageActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SET_WEATHER -> {
                        /**
                         * 测试数据
                         */
                        val weatherEntity = WeatherEntity()
                        val dataBean = WeatherEntity.DataBean()
                        weatherEntity.data = dataBean
                        val todayWeather = WeatherEntity.DataBean.TodayWeatherBean()
                        todayWeather.area = "深圳"
                        todayWeather.currentTemperature = 27
                        todayWeather.maxTemperature = 30
                        todayWeather.minTemperature = 26
                        todayWeather.weatherType = 1
                        dataBean.todayWeather = todayWeather
                        val futureWeathers =
                            mutableListOf<WeatherEntity.DataBean.FutureWeatherBean>()
                        val calendar = Calendar.getInstance()
                        for (i in 1..7) {
                            val futureWeather = WeatherEntity.DataBean.FutureWeatherBean()
                            calendar.add(Calendar.DAY_OF_MONTH, i)
                            futureWeather.dateTime = dateFormat.format(calendar.time)
                            futureWeather.maxTemperature = 30
                            futureWeather.minTemperature = 26
                            futureWeather.weatherType = 1
                            futureWeathers.add(futureWeather)
                        }
                        dataBean.futureWeather = futureWeathers
                        BaosWatchSdk.setWeather(weatherEntity)
                    }
                    OptionButton.TYPE_SET_GPS -> {
                        val sensorData = Sensor_data_gps()
                        sensorData.latitude = 22
                        sensorData.longitude = 60
                        sensorData.satellite_count = 2
                        sensorData.timespan_begin = (System.currentTimeMillis() / 1000).toInt()
                        sensorData.sum_distance_cm = 100
                        sensorData.timespan_duration = 100
                        sensorData.timespan_distance_cm = 100
                        BaosWatchSdk.sendSensorGpsData(sensorData)
                    }
                    OptionButton.TYPE_SET_DIAL -> {
                        DialActivity.getCallIntent(this)
                    }
                    OptionButton.TYPE_SYNC_DATA -> {
                        BaosWatchSdk.syncData(object : SyncDataListener {
                            override fun onSyncStart() {
                                addShowText("同步数据开始")
                            }

                            override fun onSyncFinish() {
                                addShowText("同步数据结束")
                            }

                        })
                    }
                    OptionButton.TYPE_GET_REAL_DAILY_DATA -> {
                     val dailyData=  BaosWatchSdk.getRealTimeDailyActiveData()
                        addShowText("实时每日活动数据：")
                        addShowText(getObjectString(dailyData))
                    }
                    OptionButton.TYPE_GET_REAL_HR_DATA -> {
                        val hr=  BaosWatchSdk.getRealTimeHeartRateData()
                        addShowText("实时心率数据：")
                        addShowText(getObjectString(hr))
                    }
                    OptionButton.TYPE_GET_REAL_SPO_DATA -> {
                        val spo=  BaosWatchSdk.getRealTimeDailySpoData()
                        addShowText("实时血氧数据：")
                        addShowText(getObjectString(spo))
                    }
                    OptionButton.TYPE_QUERY_DAILY_SUM -> {
                        addShowText("每日活动汇总数据：")
                        val result = BaosWatchSdk.queryDailyActiveSumData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_DAILY_HR -> {
                        addShowText("每日心率：")
                        val result = BaosWatchSdk.queryDailyHeartRateData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_SLEEP_SUM -> {
                        addShowText("每日睡眠汇总数据：")
                        val result = BaosWatchSdk.queryDailySleepSumData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_SPORT -> {
                        addShowText("运动数据：")
                        val result = BaosWatchSdk.querySportData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_RESET_HR -> {
                        addShowText("静息心率数据：")
                        val result = BaosWatchSdk.queryDailyResetHeartRateData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_OP -> {
                        addShowText("血氧数据：")
                        val result = BaosWatchSdk.queryDailyBloodOxygenData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_SPORT_MODE -> {
                        addShowText("运动模式数据：")
                        val result = BaosWatchSdk.querySportModeData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_SPORT_HR -> {
                        addShowText("运动心率数据：")
                        val result = BaosWatchSdk.querySportHeartRateData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_QUERY_SLEEP_DATA -> {
                        addShowText("睡眠状态数据：")
                        val result = BaosWatchSdk.querySleepStatusData(startTime, endTime)
                        addShowText(getObjectString(result))
                    }
                    OptionButton.TYPE_FIND_PHONE -> {
                        addShowText("打开寻找手机")
                       BaosWatchSdk.findMobile(true)
                    }
                    OptionButton.TYPE_CLOSE_FIND_PHONE -> {
                        addShowText("关闭寻找手机")
                       BaosWatchSdk.findMobile(false)
                    }
                    OptionButton.TYPE_OPEN_MUSIC -> {
                        addShowText("打开音乐控制")
                        BaosWatchSdk.musicControl(true,this)
                    }
                    OptionButton.TYPE_CLOSE_MUSIC -> {
                        addShowText("关闭音乐控制")
                        BaosWatchSdk.musicControl(false,this)
                    }
                }
            }
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

    private fun initSdkFun() {
        BaosWatchSdk.addConnectListener(connectListener)
        BaosWatchSdk.addDeviceCallBack(deviceCallBack)
    }

    private val connectListener = object : ConnectListener {
        override fun onBLEConnecting() {
            runOnUiThread {
                connectStatusTv.text = resources.getString(R.string.str_connecting)
            }

        }

        override fun onBLEConnected() {
            runOnUiThread {
                connectStatusTv.text = resources.getString(R.string.str_connected)
            }
        }

        override fun onBLEConnectFail() {
            runOnUiThread {
                connectStatusTv.text = resources.getString(R.string.str_disconnected)
            }
        }

        override fun onBLEDisConnected() {
            runOnUiThread {
                connectStatusTv.text = resources.getString(R.string.str_disconnected)
            }
        }

    }

    private val deviceCallBack = object : DeviceCallBack {


        override fun onDeviceResourceInfo(device_resource_info: Device_resource_info?) {
            addShowText("设备主动发送： 手表端发送给手机表盘和语言资源数据")
            addShowText(getObjectString(device_resource_info))
        }

        override fun onRequestWeather() {
            addShowText("设备主动发送： 手表端发起天气请求")
        }

        override fun onRequestTime() {
            addShowText("设备请求时间")
            //此处应该调用BoasWatchSdk.setTime 方法
        }

        override fun onCollectWatchLoggerRequest(data: ByteArray?) {
            addShowText("设备主动发送： 收集手表日志")
            addShowText(Arrays.toString(data))
        }


        override fun onActionSync(actionSync: Action_sync?) {
            addShowText("设备主动发送： 收到动作请求，同步给APP")
            addShowText(getObjectString(actionSync))
        }


        override fun onRequestGetData(request_get_data: Request_get_data?) {
            addShowText("设备主动发送：收到数据请求，手表发给APP")
            addShowText(getObjectString(request_get_data))
        }

        override fun onPhoneStatus(status: Int) {
            addShowText("设备主动发送：收到电话请求：$status")
        }


    }


    override fun onDestroy() {
        super.onDestroy()
        BaosWatchSdk.removeConnectListener(connectListener)
        BaosWatchSdk.removeDeviceCallBack(deviceCallBack)
    }


}