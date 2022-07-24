package com.huabao.huabaosdkdemo

import android.widget.Button
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * Author: ch
 * CreateDate: 2022/7/15 17:10
 * Description:
 */
class OptionAdapter(val datas:MutableList<OptionButton>):BaseQuickAdapter<OptionButton,BaseViewHolder>(R.layout.item_option,datas) {

    init {
        addChildClickViewIds(R.id.optionBtn)
    }

    override fun convert(holder: BaseViewHolder, item: OptionButton) {
       holder.getView<Button>(R.id.optionBtn).setText(item.buttonText)
    }
}