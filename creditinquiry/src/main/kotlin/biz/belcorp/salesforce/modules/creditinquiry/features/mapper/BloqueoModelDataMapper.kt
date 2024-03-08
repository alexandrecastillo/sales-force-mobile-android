package biz.belcorp.salesforce.modules.creditinquiry.features.mapper

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Bloqueo
import biz.belcorp.salesforce.modules.creditinquiry.features.model.BloqueoModel
import java.util.*

class BloqueoModelDataMapper {

    fun parseBloqueo(entity: Bloqueo?): BloqueoModel? {
        var bloqueo: BloqueoModel? = null

        if (entity != null) {
            bloqueo = BloqueoModel()
            bloqueo.motivoBloqueo = entity.motivoBloqueo
            bloqueo.observacion = entity.observacion
            bloqueo.tipoBloqueo = entity.tipoBloqueo
            bloqueo.item = entity.item
        }

        return bloqueo
    }

    fun parseBloqueo(collection: Collection<Bloqueo>?): List<BloqueoModel> {
        val list = ArrayList<BloqueoModel>()
        var bloqueo: BloqueoModel?

        if (collection != null) {
            for (entity in collection) {
                bloqueo = parseBloqueo(entity)
                if (bloqueo != null) {
                    list.add(bloqueo)
                }
            }
        }

        return list
    }
}
