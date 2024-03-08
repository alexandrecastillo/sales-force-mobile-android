package biz.belcorp.salesforce.messaging.core.extensions

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.differenceInExplicitDays
import biz.belcorp.salesforce.core.utils.toCalendar
import org.junit.Test
import java.util.*
import kotlin.test.assertEquals

class ExtensionsTest {

    @Test
    fun `calculate difference in days with zero`() {
        val today = Date()
        val otherDate = Date().toCalendar()
        val response = today.differenceInExplicitDays(otherDate.time)
        assertEquals(Constant.NUMBER_ZERO, response)
    }

    @Test
    fun `calculate difference in days more than zero`() {
        val today = Date()
        val otherDate = Date().toCalendar()
        otherDate.add(Calendar.DAY_OF_MONTH, -Constant.NUMBER_TWO)
        val response = today.differenceInExplicitDays(otherDate.time)
        assertEquals(Constant.NUMBER_TWO, response)
    }
}
