package biz.belcorp.salesforce.modules.postulants.utils

fun Boolean.toInt() = if (this) Constant.NUMERO_UNO else Constant.NUMERO_CERO
