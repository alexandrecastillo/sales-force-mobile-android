package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoSaldoEntity
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.TipoSaldo

class TipoSaldoEntityDataMapper {

    fun parse(entity: TipoSaldoEntity?): TipoSaldo? {
        var pojo: TipoSaldo? = null

        if (entity != null) {
            pojo = TipoSaldo()
            pojo.idSaldo = entity.idSaldo
            pojo.descripcion = entity.descripcion
        }

        return pojo
    }

    fun parse(collection: Collection<TipoSaldoEntity>): List<TipoSaldo> {
        val list = ArrayList<TipoSaldo>()
        var pojo: TipoSaldo?
        for (entity in collection) {
            pojo = parse(entity)
            if (pojo != null) {
                list.add(pojo)
            }
        }
        return list
    }

}
