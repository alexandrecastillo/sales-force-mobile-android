package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.plan.FechaNoHabilFacturacionEntity
import biz.belcorp.salesforce.core.entities.sql.plan.FechaNoHabilFacturacionEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta.FechaNoHabilMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Feriado
import com.raizlabs.android.dbflow.kotlinextensions.eq
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.where
import com.raizlabs.android.dbflow.sql.language.OperatorGroup
import com.raizlabs.android.dbflow.sql.language.Select

class FechaNoHabilDBDataStore(private val fechaNoHabilMapper: FechaNoHabilMapper) {

    fun obtener(llaveUA: LlaveUA): List<Feriado> {
        val where = ((Select(
            FechaNoHabilFacturacionEntity_Table.Fecha.distinct().withTable())
            from FechaNoHabilFacturacionEntity::class)
            where filtrarPorZonaSegunUa(llaveUA))

        return fechaNoHabilMapper.parse(where.queryList())
    }

    private fun filtrarPorZonaSegunUa(llaveUA: LlaveUA): OperatorGroup {
        val operatorGroup = OperatorGroup.clause()

        if (llaveUA.codigoZona != null) {
            operatorGroup.and(FechaNoHabilFacturacionEntity_Table.CodigoZona.withTable()
                eq llaveUA.codigoZona)
        }

        return operatorGroup
    }
}
