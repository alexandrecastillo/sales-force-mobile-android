package biz.belcorp.salesforce.modules.postulants.core.data.repository.indicadorunete.mappers

import biz.belcorp.salesforce.core.domain.entities.ua.Seccion
import biz.belcorp.salesforce.core.entities.sql.ua.SeccionEntity

class SeccionEntityDataMapper {

    fun parse(entity: SeccionEntity) = Seccion().apply {
        codigo = entity.codigo
        descripcion = entity.descripcion
        sociaEmpresaria = entity.sociaEmpresaria
    }

    fun parse(collection: Collection<SeccionEntity>): List<Seccion> {
        return collection.map { parse(it) }
    }

}
