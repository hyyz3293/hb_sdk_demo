package com.huabao.huabaosdkdemo

import me.rosuh.filepicker.filetype.FileType

/**
 *  author :chenhua
 *  create at :2022/7/17 13:15
 *  describe:
 */
class ImageFileType:FileType {
    override val fileType: String
        get() = "Image"
    override val fileIconResId: Int
        get() = R.drawable.ic_image_file_picker

    override fun verify(fileName: String): Boolean {
        /**
         * 使用 endWith 是不可靠的，因为文件名有可能是以格式结尾，但是没有 . 符号
         * 比如 文件名仅为：example_png
         */
        val isHasSuffix = fileName.contains(".")
        if (!isHasSuffix) {
            // 如果没有 . 符号，即是没有文件后缀
            return false
        }
        val suffix = fileName.substring(fileName.lastIndexOf(".") + 1)
        return when (suffix) {
            "jpeg", "jpg", "bmp", "png" -> {
                true
            }
            else -> {
                false
            }
        }
    }
}