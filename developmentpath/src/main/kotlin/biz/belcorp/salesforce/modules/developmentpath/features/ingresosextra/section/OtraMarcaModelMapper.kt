package biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra.OtraMarca
import biz.belcorp.salesforce.modules.developmentpath.features.ingresosextra.section.helper.OtraMarcaLogoFactory

class OtraMarcaModelMapper : Mapper<OtraMarcaModel, OtraMarca>() {

    override fun map(value: OtraMarcaModel): OtraMarca = OtraMarca(
        marcaDetalleId = value.marcaDetalleId,
        marcaId = value.marcaId,
        consultoraId = value.consultoraId,
        codigoRegion = value.codigoRegion,
        codigoZona = value.codigoZona,
        codigoSeccion = value.codigoSeccion,
        name = value.name,
        iconoId = value.iconoId,
        orden = value.orden,
        showFront = value.showFront,
        checked = value.checked,
        categoria = value.categoria,
        campania = value.campania
    )

    override fun reverseMap(value: OtraMarca): OtraMarcaModel {
        val logo = OtraMarcaLogoFactory.from(value.iconoId)
        return OtraMarcaModel(
            logo = logo.first,
            logoUnChecked = logo.second,
            marcaDetalleId = value.marcaDetalleId,
            marcaId = value.marcaId,
            consultoraId = value.consultoraId,
            codigoRegion = value.codigoRegion,
            codigoZona = value.codigoZona,
            codigoSeccion = value.codigoSeccion,
            name = value.name,
            iconoId = value.iconoId,
            orden = value.orden,
            showFront = value.showFront,
            checked = value.checked,
            categoria = value.categoria,
            campania = value.campania
        )
    }
}
