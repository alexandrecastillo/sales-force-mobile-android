package biz.belcorp.salesforce.modules.postulants.core.data.repository.consultacrediticia.mappers

import biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio.BloqueoEntity
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.crediticio.Bloqueo
import java.util.*

class BloqueoEntityDataMapper {

    private fun parseBloqueo(entity: BloqueoEntity?): Bloqueo? {
        var bloqueo: Bloqueo? = null

        if (entity != null) {
            bloqueo = Bloqueo()
            bloqueo.motivoBloqueo = entity.motivoBloqueo
            bloqueo.observacion = entity.observacion
            bloqueo.tipoBloqueo = entity.tipoBloqueo
            bloqueo.item = entity.item
        }

        return bloqueo
    }

    fun parseBloqueo(collection: Collection<BloqueoEntity>?): List<Bloqueo> {
        val list = ArrayList<Bloqueo>()
        var bloqueo: Bloqueo?

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
