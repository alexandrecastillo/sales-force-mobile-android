package biz.belcorp.salesforce.modules.developmentpath.widgets.etiqueta

import org.junit.Assert.assertEquals
import org.junit.Test

internal class LcmExtensionsTest {

    @Test
    fun `lcm de numeros iguales debe ser el mismo numero`() {
        val lcm = lcm(listOf(2, 2, 2))
        assertEquals(2, lcm)
    }

    @Test
    fun `lcm de numeros multiplos entre si debe ser el numero mayor`() {
        val lcm = lcm(listOf(2, 4, 8, 4))
        assertEquals(8, lcm)
    }

    @Test
    fun `lcm de numeros no multiplos entre si debe ser el producto`() {
        val lcm = lcm(listOf(3, 7, 11, 4))
        assertEquals(924, lcm)
    }

    @Test
    fun `lcm de uno y dos es dos`() {
        val lcm = lcm(listOf(1, 2, 1, 2))
        assertEquals(2, lcm)
    }
}
