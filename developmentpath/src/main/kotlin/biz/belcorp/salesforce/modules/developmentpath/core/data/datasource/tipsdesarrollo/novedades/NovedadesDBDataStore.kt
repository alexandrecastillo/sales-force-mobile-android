package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.novedades

import biz.belcorp.salesforce.core.entities.sql.novedades.NovedadesEntity
import biz.belcorp.salesforce.core.entities.sql.novedades.NovedadesEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import com.raizlabs.android.dbflow.kotlinextensions.*

class NovedadesDBDataStore {

    fun obtenerNovedades(params: Params): List<NovedadesEntity> {
        val query = (select
            from NovedadesEntity::class
            where (NovedadesEntity_Table.Grupo
            `in` params.filtrarPorNombreOpciones)
            and (NovedadesEntity_Table.Rol
            eq params.personaRol.codigoRol)
            and (NovedadesEntity_Table.CampaniaInicio lessThanOrEq params.campaniaActual)
            and (NovedadesEntity_Table.CampaniaFin greaterThanOrEq params.campaniaActual))
        return query.queryList()
    }
}
