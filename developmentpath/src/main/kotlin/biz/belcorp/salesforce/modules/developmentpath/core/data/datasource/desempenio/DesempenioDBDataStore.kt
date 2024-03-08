package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.desempenio

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.datos.DesempenioEntity
import biz.belcorp.salesforce.core.entities.sql.datos.DesempenioEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.desempenio.DesempenioMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias
import com.raizlabs.android.dbflow.kotlinextensions.*

class DesempenioDBDataStore(private val mapper: DesempenioMapper) {

    fun obtener(llaveUA: LlaveUA): List<DesempenioCampanias> {
        val where = (select from DesempenioEntity::class
            where (DesempenioEntity_Table.Region eq llaveUA.codigoRegion)
            and (DesempenioEntity_Table.Zona eq llaveUA.codigoZona)
            and (DesempenioEntity_Table.Seccion eq llaveUA.codigoSeccion))

        return mapper.parse(where.querySingle()
            ?: throw Exception("No hay desempeños para este usuario"))
    }
}
