package com.huabao.huabaosdkdemo

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.le.ScanResult
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.baos.watch.sdk.BaosWatchSdk
import cn.baos.watch.sdk.api.ScannerListener
import cn.baos.watch.sdk.utils.LogUtil
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_scan.*

/**
 * Author: ch
 * CreateDate: 2022/7/15 15:13
 * Description:
 */
class ScanActivity : AppCompatActivity() {

    private val rxPermission by lazy {
        RxPermissions(this)
    }

    private val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

    private val deviceList = mutableListOf<DeviceBean>()

    private val deviceAdapter by lazy {
        DeviceListAdapter(deviceList)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        initView()
        BaosWatchSdk.getNotificationAppList()
    }

    private fun initView() {

        deviceRecyclerView.layoutManager = LinearLayoutManager(this)
        deviceRecyclerView.adapter = deviceAdapter
        deviceRecyclerView.addItemDecoration(DividerItemDecoration(this, RecyclerView.VERTICAL))

        BaosWatchSdk.addScannerListener(scannerListener)

        share.setOnClickListener {
            rxPermission.request(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ).subscribe {
                if (it) {
                    BaosWatchSdk.shareLogFile(this@ScanActivity,)
                } else {

                }
            }

        }

        scanBtn.setOnClickListener {
            if (!bluetoothAdapter.isEnabled) {
                Toast.makeText(this@ScanActivity, R.string.str_ble_not_open, Toast.LENGTH_LONG)
                    .show()
                rxPermission.request(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).subscribe {
                    if (it) {
                        bluetoothAdapter.enable()
                    } else {
                        Toast.makeText(
                            this@ScanActivity,
                            R.string.str_open_ble,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }

                return@setOnClickListener
            }

            if (!isLocationOpen()) {
                Toast.makeText(
                    this@ScanActivity,
                    R.string.str_open_location_switch,
                    Toast.LENGTH_LONG
                )
                    .show()
                return@setOnClickListener
            }
            deviceList.clear()

            if (Build.VERSION.SDK_INT >= 31) {
                rxPermission.request(
                    Manifest.permission.BLUETOOTH_SCAN,
                    Manifest.permission.BLUETOOTH_ADVERTISE,
                    Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).subscribe {
                    if (it) {
                        BaosWatchSdk.startScan()
                    } else {
                        Toast.makeText(
                            this@ScanActivity,
                            R.string.str_open_ble,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            } else {
                rxPermission.request(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ).subscribe {
                    if (it) {
                        BaosWatchSdk.startScan()
                    } else {
                        Toast.makeText(
                            this@ScanActivity,
                            R.string.str_open_permission,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }



        }

        stopBtn.setOnClickListener {
            BaosWatchSdk.stopScan()
        }

        deviceAdapter.setOnItemClickListener { adapter, view, position ->
            if (position >= 0 && position < deviceList.size) {
                BaosWatchSdk.stopScan()
                MainActivity.getCallIntent(this, deviceList[position].deviceMac)
                finish()
            }
        }
    }


    private fun isLocationOpen(): Boolean {
        // 6.0 系统以上扫描蓝牙才需要打开定位权限
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val manager = this.getSystemService(LOCATION_SERVICE) as LocationManager
            if (null != manager) {
                //gps定位
                val isGpsProvider = manager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                //网络定位
                val isNetWorkProvider = manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
                isGpsProvider || isNetWorkProvider
            } else {
                true
            }
        } else {
            true
        }
    }

    private val scannerListener = object : ScannerListener {
        override fun onGpsNotOpen() {

        }

        override fun onBLEStartScan() {

        }

        override fun onScanning(result: ScanResult?) {
            val deviceBean = DeviceBean()
            deviceBean.deviceName = result?.device?.name

//            if (result!!.scanRecord != null && result.scanRecord!!.manufacturerSpecificData != null) {
//                val sparseArray = result.scanRecord!!.manufacturerSpecificData
//                if (sparseArray != null && sparseArray.size() > 0) {
//                    val key = sparseArray.keyAt(0)
//                    val byteMac = sparseArray[key]
//                    if (byteMac != null && byteMac.size > 0) {
//                        if (byteMac.size == 7 && byteMac[byteMac.size - 1].toInt() == 1) {
//                            deviceBean.setDeviceName("W331")
//                        } else {
//                            deviceBean.setDeviceName("W200")
//                        }
//                    }
//                }
//            }

            //                    if (result.getScanRecord() != null && result.getScanRecord().getManufacturerSpecificData() != null) {
//                        SparseArray<byte[]> sparseArray = result.getScanRecord().getManufacturerSpecificData();
//                        if (sparseArray != null && sparseArray.size() > 0) {
//                            int key = sparseArray.keyAt(0);
//                            byte[] byteMac = sparseArray.get(key);
//                            if (byteMac != null && byteMac.length > 0 ) {
//                                if (byteMac.length == 7 && byteMac[byteMac.length - 1] == 1) {
//                                    bleDeviceInfo.setDeviceName("W300");
//                                } else {
//                                    bleDeviceInfo.setDeviceName("W200");
//                                }
//                            }
//                        }
//                    }
            val deviceName = result!!.device.name
            if (!TextUtils.isEmpty(deviceName)) {
                if (deviceName.contains("W200_G50_HB")) {
                    val num: String =
                        BaosWatchSdk.changeMacAddressToFourNumber(
                            result.device.address
                        )
                    deviceBean.setDeviceName("W200$num")
                } else if (deviceName.contains("QW01")) {
                    deviceBean.setDeviceName(deviceName)
                }
            }

            deviceBean.deviceMac = result?.device?.address
            deviceBean.deviceRssi = "${result?.rssi}"

            if (!deviceList.contains(deviceBean)) {
                LogUtil.e("de-->" + deviceBean.deviceMac)
                deviceList.add(deviceBean)
                deviceRecyclerView.post {
                    deviceAdapter.notifyDataSetChanged()
                }

            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        BaosWatchSdk.removeScannerListener(scannerListener)
    }
}