package biz.belcorp.salesforce.core.domain.entities.pais

import biz.belcorp.salesforce.core.constants.CountryISO

enum class Pais(val codigoIso: String, val id: Int, val prefijo: String) {

    BOLIVIA(CountryISO.BOLIVIA, 2, "591"),
    COLOMBIA(CountryISO.COLOMBIA, 4, "57"),
    COSTARICA(CountryISO.COSTA_RICA, 5, "506"),
    CHILE(CountryISO.CHILE, 3, "56"),
    PUERTORICO(CountryISO.PUERTO_RICO, 12, "1"),
    ECUADOR(CountryISO.ECUADOR, 6, "593"),
    PERU(CountryISO.PERU, 11, "51"),
    PANAMA(CountryISO.PANAMA, 10, "507"),
    MEXICO(CountryISO.MEXICO, 9, "52"),
    SALVADOR(CountryISO.SALVADOR, 7, "503"),
    DOMINICANA(CountryISO.DOMINICANA, 13, "1"),
    GUATEMALA(CountryISO.GUATEMALA, 8, "502");

    companion object {

        fun find(pais: String): Pais? {
            values().forEach {
                if (it.codigoIso == pais)
                    return it
            }
            return null
        }

        fun find(pais: Int): Pais? {
            values().forEach {
                if (it.id == pais)
                    return it
            }
            return null
        }

        val paisesConValidacionExternaOtroTipoRechazo = listOf(
            CountryISO.DOMINICANA,
            CountryISO.PANAMA,
            CountryISO.SALVADOR
        )

        val countriesIncludeBrightPath = listOf(
            CountryISO.COLOMBIA,
            CountryISO.PERU,
            CountryISO.COSTA_RICA,
            CountryISO.BOLIVIA,
            CountryISO.ECUADOR,
            CountryISO.CHILE
        )
    }

    fun asText(): String {
        return when (this) {
            BOLIVIA -> "Bolivia"
            COLOMBIA -> "Colombia"
            COSTARICA -> "Costa Rica"
            CHILE -> "Chile"
            PUERTORICO -> "Puerto Rico"
            ECUADOR -> "Ecuador"
            PERU -> "Perú"
            PANAMA -> "Panamá"
            MEXICO -> "México"
            SALVADOR -> "El Salvador"
            DOMINICANA -> "República Dominicana"
            GUATEMALA -> "Guatemala"
        }
    }

}
