package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo

import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsNegocioEntity
import biz.belcorp.salesforce.core.entities.sql.tips.acompaniamiento.TipsNegocioEntity_Table
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.helper.Params
import com.raizlabs.android.dbflow.kotlinextensions.*

class TipsNegocioDBDataStore {

    fun obtenerTipsNegocio(params: Params): TipsNegocioEntity {
        val query = (select from TipsNegocioEntity::class
            where (TipsNegocioEntity_Table.PersonaId.withTable() eq params.personaId)
            and (TipsNegocioEntity_Table.Region eq params.ua.codigoRegion)
            and (TipsNegocioEntity_Table.Zona eq params.ua.codigoZona)
            and (TipsNegocioEntity_Table.Seccion eq params.ua.codigoSeccion)
            and (TipsNegocioEntity_Table.Opcion eq params.opcion))
        val result = query.querySingle()
        return result ?: TipsNegocioEntity()
    }
}
