package com.huabao.huabaosdkdemo;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Author: ch
 * CreateDate: 2022/7/15 15:51
 * Description:
 */
public class DeviceBean {

    private String deviceName;
    private String deviceMac;
    private String deviceRssi;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceMac() {
        return deviceMac;
    }

    public void setDeviceMac(String deviceMac) {
        this.deviceMac = deviceMac;
    }

    public String getDeviceRssi() {
        return deviceRssi;
    }

    public void setDeviceRssi(String deviceRssi) {
        this.deviceRssi = deviceRssi;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceBean that = (DeviceBean) o;
        return Objects.equals(deviceName, that.deviceName) &&
                Objects.equals(deviceMac, that.deviceMac);



    }

    @Override
    public int hashCode() {
        return 0;
    }

    @Override
    public String toString() {
        return "DeviceBean{" +
                "deviceName='" + deviceName + '\'' +
                ", deviceMac='" + deviceMac + '\'' +
                ", deviceRssi='" + deviceRssi + '\'' +
                '}';
    }
}
