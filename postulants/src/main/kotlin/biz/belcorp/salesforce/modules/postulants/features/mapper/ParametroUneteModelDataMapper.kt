package biz.belcorp.salesforce.modules.postulants.features.mapper

import biz.belcorp.salesforce.core.utils.Mapper
import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.ParametroUnete
import biz.belcorp.salesforce.modules.postulants.features.entities.ParametroUneteModel

class ParametroUneteModelDataMapper : Mapper<ParametroUnete, ParametroUneteModel>() {

    override fun map(value: ParametroUnete): ParametroUneteModel {

        val model = ParametroUneteModel()

        model.idParametroUnete = value.idParametroUnete
        model.idParametroUnetePadre = value.idParametroUnetePadre
        model.tipoParametro = value.tipoParametro
        model.nombre = value.nombre
        model.descripcion = value.descripcion
        model.valor = value.valor
        model.estado = value.estado

        return model

    }

    override fun reverseMap(value: ParametroUneteModel): ParametroUnete {

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
