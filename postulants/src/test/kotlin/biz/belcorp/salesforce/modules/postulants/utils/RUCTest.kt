package biz.belcorp.salesforce.modules.postulants.utils

import biz.belcorp.salesforce.modules.postulants.utils.algoritmos.RUC
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource


class RUCTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/utils/ruc.csv"], numLinesToSkip = 0)
    fun `test RUC Peru Validador`(
        rut: String,
        isValid: Boolean
    ) = assertEquals(isValid, RUC.validate(rut))

}
