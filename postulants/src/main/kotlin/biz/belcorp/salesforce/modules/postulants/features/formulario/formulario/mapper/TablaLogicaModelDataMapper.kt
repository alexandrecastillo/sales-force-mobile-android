package biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import biz.belcorp.salesforce.modules.postulants.features.entities.TablaLogicaModel

class TablaLogicaModelDataMapper : Mapper<TablaLogica, TablaLogicaModel>() {

    override fun reverseMap(value: TablaLogicaModel): TablaLogica {

        val model = TablaLogica()

        model.tablaLogicaDatosID = value.tablaLogicaDatosID
        model.tablaLogicaID = value.tablaLogicaID
        model.descripcion = value.descripcion
        model.codigo = value.codigo

        return model
    }

    override fun map(value: TablaLogica): TablaLogicaModel {

        val entity = TablaLogicaModel()

        entity.tablaLogicaDatosID = value.tablaLogicaDatosID
        entity.tablaLogicaID = value.tablaLogicaID
        entity.descripcion = value.descripcion
        entity.codigo = value.codigo

        return entity
    }

}
