package biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.consultants

import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraPosibleCambioNivelJoined
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultoraPosibleCambioNivelJoined_QueryTable.ConsultorasId
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultorasPdVPosibleCambioNivelEntity
import biz.belcorp.salesforce.core.entities.sql.consultora.ConsultorasPdVPosibleCambioNivelEntity_Table
import com.raizlabs.android.dbflow.kotlinextensions.*
import com.raizlabs.android.dbflow.sql.language.Select

class ConsultantsDataStore {

    fun getConsultantsMayChangeLevelListSize(section: String): Int {
        val where = (Select()
            from ConsultoraEntity::class innerJoin ConsultorasPdVPosibleCambioNivelEntity::class
            on (ConsultorasId eq ConsultorasPdVPosibleCambioNivelEntity_Table.consultoraID)
            where (ConsultorasPdVPosibleCambioNivelEntity_Table.seccion.`as`("ConsultorasPdVPosibleCambioNivel.Seccion") like section))

        return where.queryCustomList(ConsultoraPosibleCambioNivelJoined::class.java).size
    }

}
