package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.habilidades.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity
import biz.belcorp.salesforce.core.entities.sql.habilidades.HabilidadesAsignadasRDDEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*

class MisHabilidadesLocalStore {

    fun recuperarMaestros(): List<HabilidadEntity> {
        val query = select from HabilidadEntity::class
        return query.queryList()
    }

    fun recuperarDetalle(llaveUA: LlaveUA, codigoCampania: String): HabilidadesAsignadasRDDEntity? {
        val llaveUaParseada = LlaveUA(
            codigoRegion = llaveUA.codigoRegion ?: "-",
            codigoZona = llaveUA.codigoZona ?: "-",
            codigoSeccion = llaveUA.codigoSeccion ?: "-",
            consultoraId = llaveUA.consultoraId ?: -1L
        )

        val query = (select
            from HabilidadesAsignadasRDDEntity::class
            where ((HabilidadesAsignadasRDDEntity_Table.Region eq llaveUaParseada.codigoRegion)
            and (HabilidadesAsignadasRDDEntity_Table.Zona eq llaveUaParseada.codigoZona)
            and (HabilidadesAsignadasRDDEntity_Table.Seccion eq llaveUaParseada.codigoSeccion)
            and (HabilidadesAsignadasRDDEntity_Table.Campania eq codigoCampania)))

        return query.querySingle()
    }
}
