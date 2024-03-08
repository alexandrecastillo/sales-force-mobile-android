package biz.belcorp.salesforce.core.utils.documents

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource


class RUTTest {

    @ParameterizedTest
    @CsvFileSource(resources = ["/utils/documents/rut-validate.csv"], numLinesToSkip = 0)
    fun `test RUT Chile Validador`(
        rut: String,
        isValid: Boolean
    ) = assertEquals(isValid, RUT.validate(rut))

    @ParameterizedTest
    @CsvFileSource(resources = ["/utils/documents/rut-format.csv"], numLinesToSkip = 0)
    fun `test RUT Chile Formateador`(
        rutUnformatted: String,
        rutFormatted: String
    ) = assertEquals(rutFormatted, RUT.format(rutUnformatted))

}
