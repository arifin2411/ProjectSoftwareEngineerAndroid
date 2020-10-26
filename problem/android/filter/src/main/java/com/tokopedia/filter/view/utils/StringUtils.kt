package com.tokopedia.filter.view.utils

import android.annotation.SuppressLint

object StringUtils {
    @SuppressLint("DefaultLocale")
    fun formatMoney(prefix: String, money: Long, separator: Char, end: String): String? {
        return prefix + String.format("%,d", *arrayOf<Any>(java.lang.Long.valueOf(money))).replace(',', separator) + end
    }

    fun getRpCurrency(money: Long): String? {
        return formatMoney("Rp", money, '.', "")
    }
}