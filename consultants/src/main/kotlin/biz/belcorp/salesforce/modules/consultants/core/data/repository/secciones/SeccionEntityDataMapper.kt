package biz.belcorp.salesforce.modules.consultants.core.data.repository.secciones

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity

class SeccionEntityDataMapper {

    fun parse(entity: SeccionEntity?): Seccion? {
        var seccion: Seccion? = null
        if (entity != null) {
            seccion = Seccion()
            seccion.codigo = entity.codigo
        }

        return seccion
    }

    fun parse(collection: Collection<SeccionEntity>): List<Seccion> {
        val seccions = ArrayList<Seccion>(20)
        var seccion: Seccion?
        for (entity in collection) {
            seccion = parse(entity)
            if (seccion != null) {
                seccions.add(seccion)
            }
        }

        return seccions
    }
}
