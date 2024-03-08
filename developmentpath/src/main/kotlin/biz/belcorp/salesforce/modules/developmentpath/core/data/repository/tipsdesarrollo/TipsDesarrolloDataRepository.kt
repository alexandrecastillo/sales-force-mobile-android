package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.tipsdesarrollo

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.data.ConcursosDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.tipsdesarrollo.TipsDesarrolloDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.concursos.ConcursosEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.TipsDesarrolloMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.general.TipData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.TipsDesarrolloRepository

class TipsDesarrolloDataRepository(
    private val localStore: TipsDesarrolloDataStore,
    private val concursosStore: ConcursosDBDataStore,
    private val mapper: TipsDesarrolloMapper,
    private val concursosMapper: ConcursosEntityDataMapper
) : TipsDesarrolloRepository {

    override fun obtenerTipsDesarrollo(persona: PersonaRdd): TipDesarrollo {
        val tipsDesarrollo = obtenerDataTipsDesarrollo(persona)
        val concurso = obtenerConcurso(persona)
        concurso?.let { tipsDesarrollo.data.add(it) }
        return tipsDesarrollo
    }

    private fun obtenerDataTipsDesarrollo(persona: PersonaRdd): TipDesarrollo {
        val tipsDesarrollo = localStore.obtenerTipsDesarrollo(persona)
        return mapper.parse(tipsDesarrollo)
    }

    private fun obtenerConcurso(persona: PersonaRdd): TipData? {
        val concurso = concursosStore.obtenerConcurso(persona)
        return concurso?.let { concursosMapper.parseTipData(it, persona) }
    }
}
