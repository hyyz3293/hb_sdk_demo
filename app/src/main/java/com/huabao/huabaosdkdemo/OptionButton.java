package com.huabao.huabaosdkdemo;

/**
 * Author: ch
 * CreateDate: 2022/7/15 17:06
 * Description:
 */
public class OptionButton {

    private String buttonText;

    private int type;

    public static final int TYPE_CONNECT = 1;

    public static final int TYPE_SCAN = 2233;
    public static final int TYPE_SCAN_CANCEL = 3322;

    public static final int TYPE_CONNECT_BT = 100;
    public static final int TYPE_BOND = 124;

    public static final int TYPE_CONNECT_MORE = 111;
    public static final int TYPE_CONNECT_MORE_LIST = 112;
    public static final int TYPE_CONNECT_MORE_NOW = 113;
    public static final int TYPE_DISCONNECT = 2;
    public static final int TYPE_BIND_DEVICE = 3;
    public static final int TYPE_UNBIND_DEVICE = 4;
    public static final int TYPE_GET_WATCH_INFO = 5;
    public static final int TYPE_GET_BATTERY = 6;
    public static final int TYPE_FIND_PHONE = 7;
    public static final int TYPE_SET_TIME = 8;
    public static final int TYPE_SET_TIME_FORMAT12 = 9;
    public static final int TYPE_SET_TIME_FORMAT24 = 10;
    public static final int TYPE_OPEN_LIGHT_SCREEN = 11;
    public static final int TYPE_CLOSE_LIGHT_SCREEN = 12;
    public static final int TYPE_SET_LONG_SIT = 13;
    public static final int TYPE_SET_REGULAR_REMIND = 14;
    public static final int TYPE_SET_WOMEN_HEALTH = 15;
    public static final int TYPE_SET_NO_DISTURB = 16;
    public static final int TYPE_SET_HR_MONITOR = 17;
    public static final int TYPE_SET_MESSAGE = 18;
    public static final int TYPE_SET_WEATHER = 19;
    public static final int TYPE_SET_GPS = 20;
    public static final int TYPE_SET_DIAL = 21;
    public static final int TYPE_SYNC_DATA = 22;
    public static final int TYPE_GET_REAL_DAILY_DATA = 23;
    public static final int TYPE_CLOSE_FIND_PHONE = 24;
    public static final int TYPE_QUERY_DAILY_SUM = 25;
    public static final int TYPE_QUERY_DAILY_HR = 26;
    public static final int TYPE_QUERY_SLEEP_SUM = 27;
    public static final int TYPE_QUERY_SPORT = 28;
    public static final int TYPE_QUERY_RESET_HR = 29;
    public static final int TYPE_QUERY_OP = 30;
    public static final int TYPE_QUERY_SPORT_MODE = 31;
    public static final int TYPE_QUERY_SPORT_HR = 32;
    public static final int TYPE_QUERY_SLEEP_DATA = 33;
    public static final int TYPE_SET_USER = 34;
    public static final int TYPE_OPEN_MUSIC = 35;
    public static final int TYPE_CLOSE_MUSIC = 36;
    public static final int TYPE_GET_REAL_HR_DATA =37;
    public static final int TYPE_GET_REAL_SPO_DATA =38;
    public static final int TYPE_GET_CONTACT = 39;
    public static final int TYPE_GET_CONTACT_SET = 40;
    public static final int TYPE_PRAYER_TIME = 41;
    public static final int TYPE_PRAYER_GPS_TIME = 42;
    public static final int TYPE_CONNECT_MORE_DEVICE = 43;

    public OptionButton(String buttonText, int type) {
        this.buttonText = buttonText;
        this.type = type;
    }

    public String getButtonText() {
        return buttonText;
    }

    public void setButtonText(String buttonText) {
        this.buttonText = buttonText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
