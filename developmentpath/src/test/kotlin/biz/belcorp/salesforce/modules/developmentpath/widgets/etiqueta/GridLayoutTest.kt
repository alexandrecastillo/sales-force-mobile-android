package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import org.junit.Assert.assertEquals
import org.junit.Test

internal class GridLayoutTest {

    @Test
    fun `multiplicar 1, 2, y 3 da 6`() {
        assertEquals(6, listOf(1, 2, 3).multiplyBy { it })
    }
}
