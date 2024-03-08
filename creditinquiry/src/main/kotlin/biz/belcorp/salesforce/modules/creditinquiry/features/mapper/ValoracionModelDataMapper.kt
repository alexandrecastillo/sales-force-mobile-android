package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Valoracion
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ValoracionModel
import java.util.*

class ValoracionModelDataMapper {

    fun parseValoracion(entity: Valoracion?): ValoracionModel? {
        var v: ValoracionModel? = null

        if (entity != null) {
            v = ValoracionModel()
            v.tipo = entity.tipo
            v.valor = entity.valor
        }

        return v
    }

    fun parseValoracion(collection: Collection<Valoracion>?): List<ValoracionModel> {
        val list = ArrayList<ValoracionModel>()

        collection?.forEach { entity ->
            val v = parseValoracion(entity)
            if (v != null) {
                list.add(v)
            }
        }

        return list
    }
}
