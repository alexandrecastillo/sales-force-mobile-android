package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ValoracionEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Valoracion
import java.util.*

class ValoracionEntityDataMapper {

    private fun parseValoracion(entity: ValoracionEntity?): Valoracion? {
        var v: Valoracion? = null

        if (entity != null) {
            v = Valoracion()
            v.valor = entity.valor
            v.tipo = entity.tipo
        }

        return v
    }

    fun parseValoracion(collection: Collection<ValoracionEntity>): List<Valoracion> {
        val list = ArrayList<Valoracion>()
        var v: Valoracion?

        for (entity in collection) {
            v = parseValoracion(entity)

            if (v != null) {
                list.add(v)
            }
        }

        return list
    }
}
