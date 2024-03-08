package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsDesarrolloEntity_Table
import biz.belcorp.salesforce.core.utils.negativoSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import com.raizlabs.android.dbflow.kotlinextensions.*

class TipsDesarrolloDataStore {

    fun obtenerTipsDesarrollo(persona: PersonaRdd): TipsDesarrolloEntity {
        val query = (select
            from TipsDesarrolloEntity::class
            where (TipsDesarrolloEntity_Table.PersonaId eq persona.llaveUA.consultoraId.negativoSiNull())
            and (TipsDesarrolloEntity_Table.Region eq persona.llaveUA.codigoRegion)
            and (TipsDesarrolloEntity_Table.Zona eq persona.llaveUA.codigoZona)
            and (TipsDesarrolloEntity_Table.Seccion eq persona.llaveUA.codigoSeccion))
        return query.querySingle() ?: TipsDesarrolloEntity()
    }
}
