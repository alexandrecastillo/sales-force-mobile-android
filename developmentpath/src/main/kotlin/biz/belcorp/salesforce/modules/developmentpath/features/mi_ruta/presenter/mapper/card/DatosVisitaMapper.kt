package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.card

import biz.belcorp.salesforce.core.utils.horaFormateada
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.card.MiRutaCardModel
import java.util.*

class DatosVisitaMapper{

    fun mapPlanificada(entity: Visita): MiRutaCardModel.DatosVisita {
        return MiRutaCardModel.DatosVisita(
                id = entity.id,
                hora = entity.horaAMostrar.horaFormateada(),
                mostrarPlanificar = false,
                mostrarVisitaRegistrada = entity.estaRegistrada,
                fecha = entity.horaAMostrar)
    }

    fun mapNoPlanificada(entity: Visita): MiRutaCardModel.DatosVisita {
        return MiRutaCardModel.DatosVisita(
                id = entity.id,
                hora = null,
                mostrarPlanificar = true,
                mostrarVisitaRegistrada = false,
                fecha = Date())
    }
}
