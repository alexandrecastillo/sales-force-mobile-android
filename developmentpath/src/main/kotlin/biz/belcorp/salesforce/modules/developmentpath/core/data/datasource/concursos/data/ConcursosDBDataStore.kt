package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.data

import biz.belcorp.salesforce.core.entities.sql.concursos.ConcursosEntity
import biz.belcorp.salesforce.core.entities.sql.concursos.ConcursosEntity_Table
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import com.raizlabs.android.dbflow.kotlinextensions.*

class ConcursosDBDataStore {

    fun obtenerConcurso(persona: PersonaRdd): ConcursosEntity? {
        val query = (select
            from ConcursosEntity::class
            where (ConcursosEntity_Table.PersonaId eq persona.id)
            and (ConcursosEntity_Table.Region eq persona.llaveUA.codigoRegion.guionSiNull())
            and (ConcursosEntity_Table.Zona eq persona.llaveUA.codigoZona.guionSiNull())
            and (ConcursosEntity_Table.Seccion eq persona.llaveUA.codigoSeccion.guionSiNull()))
        return query.querySingle()
    }

    fun guardar(concursosEntity: ConcursosEntity): Boolean {
        return concursosEntity.save()
    }

}
