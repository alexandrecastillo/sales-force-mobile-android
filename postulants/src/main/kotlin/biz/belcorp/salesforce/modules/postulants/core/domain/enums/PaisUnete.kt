package biz.belcorp.salesforce.modules.postulants.core.domain.enums

import biz.belcorp.salesforce.core.domain.entities.pais.Pais

object PaisUnete {

    val paisesCam = listOf(
        Pais.PANAMA.codigoIso,
        Pais.COSTARICA.codigoIso,
        Pais.GUATEMALA.codigoIso,
        Pais.SALVADOR.codigoIso
    )

    val paisesBuro = listOf(
        Pais.COLOMBIA.codigoIso
    )

    val paisesSMS = listOf(
        Pais.COLOMBIA.codigoIso,
        Pais.PERU.codigoIso,
        Pais.SALVADOR.codigoIso,
        Pais.GUATEMALA.codigoIso,
        Pais.ECUADOR.codigoIso
    )

    val paisesGZSMSAprobation = listOf(
        Pais.PERU.codigoIso,
        Pais.SALVADOR.codigoIso,
        Pais.GUATEMALA.codigoIso
    )

    val paisesPagoContado = listOf(
        Pais.ECUADOR.codigoIso,
        Pais.PERU.codigoIso,
        Pais.CHILE.codigoIso,
        Pais.MEXICO.codigoIso,
        Pais.COSTARICA.codigoIso,
        Pais.GUATEMALA.codigoIso,
        Pais.DOMINICANA.codigoIso,
        Pais.COLOMBIA.codigoIso,
        Pais.PANAMA.codigoIso,
        Pais.SALVADOR.codigoIso
    )

    val paisesPagoContadoConImagenes = listOf(
        Pais.DOMINICANA.codigoIso
    )

    val paisesConValidacionExterna = listOf(
        Pais.COSTARICA.codigoIso,
        Pais.ECUADOR.codigoIso,
        Pais.PERU.codigoIso,
        Pais.GUATEMALA.codigoIso,
        Pais.DOMINICANA.codigoIso,
        Pais.PANAMA.codigoIso,
        Pais.SALVADOR.codigoIso
    )

    val paisesAllErrors = listOf(
        Pais.PERU.codigoIso,
        Pais.CHILE.codigoIso
    )

    val paisesContadorSMS = listOf(
        Pais.COLOMBIA.codigoIso
    )

    val paisesConPlaceId = listOf(
        Pais.CHILE.codigoIso,
        Pais.PERU.codigoIso
    )

}
