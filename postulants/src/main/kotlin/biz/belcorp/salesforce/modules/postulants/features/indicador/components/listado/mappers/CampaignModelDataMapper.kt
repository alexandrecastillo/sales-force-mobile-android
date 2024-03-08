package biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.mappers

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.features.entities.CampaniaModel

class CampaignModelDataMapper : Mapper<Campania, CampaniaModel>() {
    override fun map(value: Campania): CampaniaModel {
        val model = CampaniaModel()

        model.codigo = value.codigo
        model.nombreCorto = value.nombreCorto
        model.inicio = value.inicio
        model.fin = value.fin
        model.inicioFacturacion = value.inicioFacturacion
        return model
    }

    override fun reverseMap(value: CampaniaModel): Campania {
        return Campania(
            value.codigo,
            value.nombreCorto,
            value.inicio,
            value.fin,
            value.inicioFacturacion,
            value.orden,
            value.esPrimerDiaFacturacion,
            null
        )

    }
}
