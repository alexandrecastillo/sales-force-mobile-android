package biz.belcorp.salesforce.modules.creditinquiry.core.data.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.data.entity.ExplicacionDeudaEntity
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Explicacion
import java.util.*

class ExplicacionEntityDataMapper {

    private fun parseExplicacion(entity: ExplicacionDeudaEntity?): Explicacion? {
        var deudaExterna: Explicacion? = null

        if (entity != null) {
            deudaExterna = Explicacion()
            deudaExterna.valor = entity.valor
        }

        return deudaExterna
    }

    fun parseExplicacion(collection: Collection<ExplicacionDeudaEntity>): List<Explicacion> {
        val list = ArrayList<Explicacion>()
        var consultaCrediticia: Explicacion?

        for (entity in collection) {
            consultaCrediticia = parseExplicacion(entity)

            if (consultaCrediticia != null) {
                list.add(consultaCrediticia)
            }
        }

        return list
    }
}
