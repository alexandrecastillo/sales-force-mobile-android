package biz.belcorp.salesforce.core.data.repository.uainfo.cloud.dto

import biz.belcorp.salesforce.core.domain.entities.people.Person
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol

class UaInfoQuery {

    fun create(person: Person): Map<String, String> {
        return when (person.role) {
            Rol.DIRECTOR_VENTAS -> createDVQuery()
            Rol.GERENTE_REGION -> createGRQuery(person.uaKey)
            Rol.GERENTE_ZONA -> createGZQuery(person.uaKey)
            Rol.SOCIA_EMPRESARIA -> createSEQuery(person.uaKey)
            else -> emptyMap()
        }
    }

    private fun createDVQuery(): Map<String, String> {
        return mapOf(
            SHOW_REGIONS to true.toString(),
            SHOW_ZONES to true.toString()
        )
    }

    private fun createGRQuery(uaKey: LlaveUA): Map<String, String> {
        return mapOf(
            REGION_CODE to uaKey.codigoRegion.orEmpty(),
            SHOW_ZONES to true.toString(),
            SHOW_SECTIONS to true.toString()
        )
    }

    private fun createGZQuery(uaKey: LlaveUA): Map<String, String> {
        return mapOf(
            REGION_CODE to uaKey.codigoRegion.orEmpty(),
            ZONE_CODE to uaKey.codigoZona.orEmpty(),
            SHOW_SECTIONS to true.toString()
        )
    }

    private fun createSEQuery(uaKey: LlaveUA): Map<String, String> {
        return mapOf(
            REGION_CODE to uaKey.codigoRegion.orEmpty(),
            ZONE_CODE to uaKey.codigoZona.orEmpty(),
            SECTION_CODE to uaKey.codigoSeccion.orEmpty()
        )
    }

    companion object {
        private const val REGION_CODE = "region_code"
        private const val ZONE_CODE = "zone_code"
        private const val SECTION_CODE = "section_code"
        private const val SHOW_REGIONS = "show_regions"
        private const val SHOW_ZONES = "show_zones"
        private const val SHOW_SECTIONS = "show_sections"
    }
}
