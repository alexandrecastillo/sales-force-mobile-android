package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.RFC
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource


class RFCTest {
    @ParameterizedTest
    @CsvFileSource(resources = ["/utils/rfc.csv"], numLinesToSkip = 0)
    fun testGeneracionRFC(
        rfcExpectedValue: String,
        nombres: String,
        apellidoPaterno: String,
        apellidoMaterno: String?,
        diaNacimiento: String,
        mesNacimiento: String,
        annioNacimiento: String
    ) {

        val actualValue = RFC.Builder()
            .nombres(nombres)
            .apellidoPaterno(apellidoPaterno)
            .apellidoMaterno(apellidoMaterno ?: "")
            .fechaNacimiento(diaNacimiento, mesNacimiento, annioNacimiento)
            .build()

        assertEquals(rfcExpectedValue, actualValue)
    }
}
