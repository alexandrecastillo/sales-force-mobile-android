package biz.belcorp.salesforce.modules.consultants.features.search.mappers

import biz.belcorp.salesforce.core.features.search.models.TipoSaldoModel
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoSaldo

class TipoSaldoModelDataMapper {

    fun parse(list: List<TipoSaldo>): List<TipoSaldoModel> {
        return list.map { parse(it) }
    }

    fun parse(pojo: TipoSaldo): TipoSaldoModel {
        return TipoSaldoModel().apply {
            idSaldo = pojo.idSaldo
            descripcion = pojo.descripcion
        }
    }

}
