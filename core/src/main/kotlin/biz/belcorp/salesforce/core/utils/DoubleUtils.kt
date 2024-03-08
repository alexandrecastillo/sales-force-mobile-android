package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL

fun Double.gtZero(): Boolean = this > ZERO_DECIMAL

fun Double.ltZero(): Boolean = this < ZERO_DECIMAL

fun Double.isZero(): Boolean = this == ZERO_DECIMAL

fun Double.isNotZero(): Boolean = !isZero()
