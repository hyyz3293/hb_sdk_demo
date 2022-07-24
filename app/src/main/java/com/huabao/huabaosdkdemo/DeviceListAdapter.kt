package com.huabao.huabaosdkdemo

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Author: ch
 * CreateDate: 2022/7/15 15:47
 * Description:
 */
class DeviceListAdapter(val datas: MutableList<DeviceBean>) :
    BaseQuickAdapter<DeviceBean, BaseViewHolder>(R.layout.item_device, datas) {

    override fun convert(holder: BaseViewHolder, item: DeviceBean) {
        holder.setText(R.id.deviceNameTv,item.deviceName)
            .setText(R.id.deviceMacTv,item.deviceMac)
            .setText(R.id.rssiTv,item.deviceRssi)
    }
}