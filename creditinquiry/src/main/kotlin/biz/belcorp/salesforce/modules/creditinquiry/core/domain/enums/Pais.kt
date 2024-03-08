package biz.belcorp.salesforce.modules.creditinquiry.core.domain.enums

enum class Pais(val codigoIso: String, val id: Int, val prefijo: String) {

    BOLIVIA("BO", 2, "591"),
    COLOMBIA("CO", 4, "57"),
    COSTARICA("CR", 5, "506"),
    CHILE("CL", 3, "56"),
    PUERTORICO("PR", 12, "1"),
    ECUADOR("EC", 6, "593"),
    PERU("PE", 11, "51"),
    PANAMA("PA", 10, "507"),
    MEXICO("MX", 9, "52"),
    SALVADOR("SV", 7, "503"),
    DOMINICANA("DO", 13, "1"),
    GUATEMALA("GT", 8, "502");

    companion object {

        val paisesCam = listOf(
            PANAMA.codigoIso,
            COSTARICA.codigoIso,
            GUATEMALA.codigoIso,
            SALVADOR.codigoIso
        )

        val paisesBuro = listOf(
            COLOMBIA.codigoIso
        )

        val paisesSMS = listOf(
            COLOMBIA.codigoIso,
            PERU.codigoIso
        )

        fun find(pais: String): Pais? {
            Pais.values().forEach {
                if (it.codigoIso == pais)
                    return it
            }
            return null
        }

        fun find(pais: Int): Pais? {
            Pais.values().forEach {
                if (it.id == pais)
                    return it
            }
            return null
        }

    }

}
