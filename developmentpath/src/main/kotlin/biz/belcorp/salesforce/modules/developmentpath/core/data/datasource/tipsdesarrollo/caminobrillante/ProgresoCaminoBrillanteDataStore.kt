package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.caminobrillante

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelActualCaminoBrillanteEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelActualCaminoBrillanteEntity_Table
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.NivelesCaminoBrillanteEntity
import com.raizlabs.android.dbflow.kotlinextensions.*

class ProgresoCaminoBrillanteDataStore {

    fun obtenerProgreso(llaveUA: LlaveUA): NivelActualCaminoBrillanteEntity {
        val query = (select from NivelActualCaminoBrillanteEntity::class
            where ((NivelActualCaminoBrillanteEntity_Table.ConsultoraId eq llaveUA.consultoraId)
            and (NivelActualCaminoBrillanteEntity_Table.Region eq llaveUA.codigoRegion)
            and (NivelActualCaminoBrillanteEntity_Table.Zona eq llaveUA.codigoZona)
            and (NivelActualCaminoBrillanteEntity_Table.Seccion eq llaveUA.codigoSeccion)))
        return query.querySingle() ?: NivelActualCaminoBrillanteEntity()
    }

    fun obtenerNiveles(): List<NivelesCaminoBrillanteEntity> {
        return (select from NivelesCaminoBrillanteEntity::class).queryList()
    }
}
