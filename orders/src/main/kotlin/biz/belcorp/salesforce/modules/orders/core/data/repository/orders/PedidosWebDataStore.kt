package biz.belcorp.salesforce.modules.orders.core.data.repository.orders


import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity
import biz.belcorp.salesforce.core.entities.sql.unete.ParametroUneteEntity_Table
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.BLOQUEOPEDIDO
import biz.belcorp.salesforce.modules.orders.core.domain.contants.Constants.STATUS_ENABLE
import com.raizlabs.android.dbflow.sql.language.Select


class PedidosWebDataStore {

    fun getLockParam(): ParametroUneteEntity? {
        return Select().from(ParametroUneteEntity::class.java)
            .where(ParametroUneteEntity_Table.FK_IdTipoParametro.eq(BLOQUEOPEDIDO),
                ParametroUneteEntity_Table.Estado.eq(STATUS_ENABLE))
            .querySingle()
    }

}
