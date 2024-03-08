package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.tipsoferta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipOfertaEntity_Table
import biz.belcorp.salesforce.core.utils.save
import com.raizlabs.android.dbflow.kotlinextensions.*

class TipsOfertaDBDataStore {

    fun obtenerTipsOferta(personaId: Long, llaveUA: LlaveUA): List<TipOfertaEntity> {
        val query = (select from TipOfertaEntity::class
            where (TipOfertaEntity_Table.PersonaId eq personaId)
            and (TipOfertaEntity_Table.Region eq llaveUA.codigoRegion)
            and (TipOfertaEntity_Table.Zona eq llaveUA.codigoZona)
            and (TipOfertaEntity_Table.Seccion eq llaveUA.codigoSeccion))
        return query.queryList().sortedBy { tip -> tip.orden }
    }

    fun actualizarData(data: List<TipOfertaEntity>) {
        data.save()
    }
}
