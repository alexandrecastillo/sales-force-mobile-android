package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.tipsdesarrollo.detail.ventaganancia.tips.model.OfertaModel

class OfertaViewMapper: Mapper<Oferta, OfertaModel>() {

    override fun map(value: Oferta): OfertaModel {
        return OfertaModel(
            cuv = value.cuv,
            tipo = value.tipo,
            marca = value.marca,
            productoNombre = value.productoNombre,
            productoPrecio = value.productoPrecio,
            productoImagen = value.productoImagen,
            productoUrl = value.productoUrl
        )
    }

    override fun reverseMap(value: OfertaModel) = Oferta()
}
