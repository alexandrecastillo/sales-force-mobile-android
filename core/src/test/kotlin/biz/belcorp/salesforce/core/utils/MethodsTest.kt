package biz.belcorp.salesforce.core.utils

import biz.belcorp.salesforce.core.constants.Constant
import org.amshove.kluent.shouldEqual
import org.junit.Test
import java.util.*

class MethodsTest {

    private val calendar by lazy { Calendar.getInstance() }

    @Test
    fun `Test calculateDays zero`() {
        val days = calculateDays(null, null)
        days shouldEqual Constant.NUMERO_CERO
    }

    @Test
    fun `Test calculateDays four`() {
        calendar.time = Date()
        calendar.add(Calendar.DATE, -Constant.NUMERO_TRES)

        val days = calculateDays(Date(), calendar.time)
        days shouldEqual 4
    }

    private fun calculateDays(date: Date?, compareDate: Date?): Int {
        if (date == null || compareDate == null) return 0
        return (date differenceInDays compareDate) + Constant.NUMERO_UNO
    }
}
