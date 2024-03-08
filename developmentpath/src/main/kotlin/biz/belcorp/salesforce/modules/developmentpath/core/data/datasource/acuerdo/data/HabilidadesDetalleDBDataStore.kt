package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.acuerdo.data

import biz.belcorp.salesforce.core.entities.sql.habilidades.DetalleHabilidadesLiderazgoRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.habilidades.HabilidadesLiderazgoDetalleMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.habilidades.detalle.HabilidadesDetalleRepository
import com.raizlabs.android.dbflow.kotlinextensions.from
import com.raizlabs.android.dbflow.kotlinextensions.select

class HabilidadesDetalleDBDataStore(val mapper: HabilidadesLiderazgoDetalleMapper) :
    HabilidadesDetalleRepository {

    override fun obtener(): List<HabilidadesDetalleRepository.HabilidadesLiderazgoDetalle> {
        val where = (select from DetalleHabilidadesLiderazgoRDDEntity::class)
        return where.queryList().map { mapper.parse(it) }
    }
}
