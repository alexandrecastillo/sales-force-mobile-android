package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.concursos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.core.entities.sql.concursos.ConcursosEntity
import biz.belcorp.salesforce.core.utils.guionSiNull
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.Concurso
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData

class ConcursosEntityDataMapper: ConcursosEntityDataMapperBase() {

    override fun parse(entidad: ConcursosEntity): Concurso {
        return Concurso(
            personaId = entidad.personaId,
            region = entidad.region,
            zona = entidad.zona,
            seccion = entidad.seccion,
            data = crearConcursosDetalle(entidad.data))
    }

    fun parse(persona: Persona, data: String): ConcursosEntity {
        return ConcursosEntity().apply {
            personaId = persona.id
            region = persona.llaveUA.codigoRegion.guionSiNull()
            zona = persona.llaveUA.codigoZona.guionSiNull()
            seccion = persona.llaveUA.codigoSeccion.guionSiNull()
            this.data = data
        }
    }

    fun parseTipData(entidad: ConcursosEntity, persona: Persona): TipData {
        val concurso = parse(entidad)
        return crearConcursoTipData(concurso, persona)
    }
}
