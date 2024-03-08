package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Explicacion
import biz.belcorp.salesforce.modules.creditinquiry.features.model.ExplicacionModel
import java.util.*

class ExplicacionModelDataMapper {

    fun parseExplicacion(entity: Explicacion?): ExplicacionModel? {
        var e: ExplicacionModel? = null

        if (entity != null) {
            e = ExplicacionModel()
            e.valor = entity.valor
        }

        return e
    }

    fun parseExplicacion(collection: Collection<Explicacion>?): List<ExplicacionModel> {
        val list = ArrayList<ExplicacionModel>()

        collection?.forEach { entity ->
            val exp = parseExplicacion(entity)
            if (exp != null) {
                list.add(exp)
            }
        }

        return list
    }
}
