package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

import biz.belcorp.salesforce.core.utils.toDescriptionDay
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.Acuerdo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.acuerdos.historico.AcuerdosPorCampania
import biz.belcorp.salesforce.modules.developmentpath.utils.numeroCampania

class AcuerdosHistoricosModelMapper {

    fun convertir(entidad: AcuerdosPorCampania): AcuerdosPorCampaniaModel {
        return AcuerdosPorCampaniaModel(
            entidad.campania.codigo,
            entidad.campania.numeroCampania(),
            entidad.acuerdos.map { convertir(it) }
        )
    }

    fun convertir(entidades: List<AcuerdosPorCampania>): List<AcuerdosPorCampaniaModel> {
        return entidades.map { convertir(it) }
    }

    fun convertir(entidad: Acuerdo): AcuerdoModel {
        return AcuerdoModel(
            entidad.id,
            entidad.contenido,
            entidad.fechaCreacion.toDescriptionDay() ?: "",
            entidad.cumplido
        )
    }
}
