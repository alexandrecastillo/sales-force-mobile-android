package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.foco

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.focos.CabeceraFoco
import biz.belcorp.salesforce.modules.developmentpath.core.domain.foco.FocoModel

class FocoModelMapper {

    fun parse(model: CabeceraFoco) = FocoModel().apply {
        nombre = model.nombre
        descripcion = model.descripcion
        descripcionDetalle = model.descripcionDetalle
        rutaImagen = model.rutaImagen
    }

    fun parse(models: List<CabeceraFoco>): List<FocoModel> {
        val focoModels = mutableListOf<FocoModel>()
        models.forEach {
            focoModels.add(parse(it))
        }
        return focoModels
    }
}
