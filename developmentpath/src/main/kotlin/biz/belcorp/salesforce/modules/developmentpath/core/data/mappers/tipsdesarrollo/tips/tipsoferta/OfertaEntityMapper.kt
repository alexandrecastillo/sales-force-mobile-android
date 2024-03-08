package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.tipsdesarrollo.tips.tipsoferta

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsoferta.OfertaEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.tips.tipsoferta.Oferta

class OfertaEntityMapper : Mapper<OfertaEntity, Oferta>() {
    override fun map(value: OfertaEntity): Oferta {
        return Oferta(
            cuv = value.cuv,
            tipo = value.tipo,
            marca = value.marca,
            productoNombre = value.productoNombre,
            productoPrecio = value.productoPrecio,
            productoImagen = value.productoImagen,
            productoUrl = value.productoUrl)
    }

    override fun reverseMap(value: Oferta): OfertaEntity {
        return OfertaEntity(
            cuv = value.cuv,
            tipo = value.tipo,
            marca = value.marca,
            productoNombre = value.productoNombre,
            productoPrecio = value.productoPrecio,
            productoImagen = value.productoImagen,
            productoUrl = value.productoUrl)
    }
}
