package biz.belcorp.salesforce.modules.postulants.core.data.repository.parametrounete

import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete

class ParametroUneteEntityDataMapper : Mapper<ParametroUnete, ParametroUneteEntity>() {

    override fun map(value: ParametroUnete) = ParametroUneteEntity()

    override fun reverseMap(value: ParametroUneteEntity): ParametroUnete {
        val entity = ParametroUnete()
        entity.idParametroUnete = value.idParametroUnete
        entity.idParametroUnetePadre = value.idParametroUnetePadre
        entity.tipoParametro = value.tipoParametro
        entity.nombre = value.nombre
        entity.descripcion = value.descripcion
        entity.valor = value.valor
        entity.estado = value.estado
        return entity
    }

}
