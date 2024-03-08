package biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica

import biz.belcorp.salesforce.core.entities.sql.unete.TablaLogicaEntity
import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica

class TablaLogicaEntityDataMapper : Mapper<TablaLogica, TablaLogicaEntity>() {

    override fun map(value: TablaLogica): TablaLogicaEntity {
        throw UnsupportedOperationException()
    }

    override fun reverseMap(value: TablaLogicaEntity): TablaLogica {
        val entity = TablaLogica()
        entity.codigo = value.codigo
        entity.descripcion = value.descripcion
        entity.tablaLogicaDatosID = value.id
        entity.tablaLogicaID = value.tablaLogicaID
        return entity
    }
}
