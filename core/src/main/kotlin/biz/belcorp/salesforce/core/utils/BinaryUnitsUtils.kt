package biz.belcorp.salesforce.core.utils

object BinaryUnitsUtils {

    private const val FACTOR_1024 = 1024

    fun bytesToMB(bytes: Double): Double =
        bytes / (FACTOR_1024 * FACTOR_1024)

}
